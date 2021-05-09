// Kamil Matejuk
// 250135
// Programowanie Współbierzne
// Labolatoria 2, Zadanie 1

package main

import (
	"fmt"
	"math"
	"math/rand"
	"os"
	"os/exec"
	"reflect"
	"strconv"
	"strings"
	"sync"
	"time"
)

/* ***********************************************************************
**************************************************************************
****************************  Vertex struct  *****************************
**************************************************************************
*************************************************************************/

type Vertex struct {
	index            int         // numer
	receivedPackets  []int       // lista pakietów które przeszły przez ten wierzchołek
	currentPacket    int         // pakiet aktualnie przechowywany w wierzcholku
	receivingChanels []*chan int // lista kanałów z których można otrzymać pakiet
	sendingChanels   []*chan int // lista kanałów do których można wysłać pakiet
}

// odebranie pakietu
func (v *Vertex) receive() {
	// sprawdź czy wierzchołek jest wolny
	if v.currentPacket == 0 {

		// odbierz pakiet z kanału, z którego pierwsze coś wyjdzie
		cases := make([]reflect.SelectCase, len(v.receivingChanels))
		for i, ch := range v.receivingChanels {
			cases[i] = reflect.SelectCase{Dir: reflect.SelectRecv, Chan: reflect.ValueOf(*ch)}
		}
		_, value, _ := reflect.Select(cases)
		packet := int(value.Int())

		// zapisz pakiet w wierzchołku
		v.currentPacket = packet

		// dodaj pakiet do listy odebranych w danym wierzchołku
		found := false
		for _, p := range v.receivedPackets {
			if p == packet {
				found = true
			}
		}
		if !found {
			v.receivedPackets = append(v.receivedPackets, packet)
		}

		// dodaj dany wierzchołek do ścieżki danego pakietu
		allPackets[packet] = append(allPackets[packet], v.index)

		// wypisz komunikat
		fmt.Printf("[Wierzchołek %d] \tPrzechowuje pakiet %d\n", v.index, packet)
	}
}

// przesunięcie pakietu na kolejne miejsce
func (v *Vertex) send(receivingChannel *chan int) {
	// sprawdź czy wierzchołek przechowuje pakiet
	if v.currentPacket != 0 {

		// sprawdź czy pakiet jeszcze może żyć
		if len(allPackets[v.currentPacket]) >= PACKET_LIFETIME {
			fmt.Printf("[Wierzchołek %d] \tŚmierć pakietu %d\n", v.index, v.currentPacket)
			v.currentPacket = 0
			receivedPackets++
			*receivingChannel <- -1
		} else {
			// ustaw przechowywany pakiet na zerowy
			p := v.currentPacket
			v.currentPacket = 0

			// wylosuj kanał do wysłania
			n := rand.Intn(len(v.sendingChanels))
			if v.index == NUMBER_OF_VERTICES-1 {
				n = int(math.Min(float64(rand.Intn(2*len(v.sendingChanels))), float64(len(v.sendingChanels)-1)))
			}

			// wyślij
			next := v.sendingChanels[n]
			*next <- p
		}
	}
}

/* ***********************************************************************
**************************************************************************
*****************************  Graph struct  *****************************
**************************************************************************
*************************************************************************/

type Graph struct {
	vertices        []*Vertex  // lista wierzchołków
	edges           [][2]int   // lista krawędzi
	packetNumber    int        // numer pierwszego pakietu
	receivedPackets []int      // lista odebranych pakietów
	channels        []chan int // lista otwartych kanałów
}

// stworzenie grafu cyklicznego
func GenerateGraph(numberOfVertices int, numberOfExtraEdges int, numberOfExtraReversedEdges int) Graph {

	// sprawdzenie czy może istnieć graf o zadanej ilości krawędzi
	maxVerices := numberOfVertices * (numberOfVertices - 1)
	if numberOfVertices+numberOfExtraEdges > maxVerices {
		fmt.Printf("Błąd: ilość krawędzi w grafie nie może przekroczyć n(n-1) = %d\n", maxVerices)
		os.Exit(1)
	}

	// utworzenie n wierzchołków
	vertices := []*Vertex{}
	for i := 0; i < numberOfVertices; i++ {
		v := Vertex{i, []int{}, 0, []*chan int{}, []*chan int{}}
		vertices = append(vertices, &v)
	}
	g := Graph{vertices, [][2]int{}, 1, []int{}, []chan int{}}

	// utworzenie cyklu
	// łącząc wierzchołki (i, i+1) dla każdego i
	for i := 0; i < numberOfVertices; i++ {
		v1 := vertices[i]
		v2 := vertices[(i+1)%numberOfVertices]
		g.addEdge(v1, v2)
	}

	// dodanie losowych dodatkowych krawędzi
	i := 0
	rand.Seed(time.Now().UnixNano())
	for i < numberOfExtraEdges {
		start := rand.Intn(numberOfVertices - 2)
		end := rand.Intn(numberOfVertices - 1)
		if start < end {
			v1 := vertices[start]
			v2 := vertices[end]
			if g.addEdge(v1, v2) {
				i++
			}
		}
	}

	// dodanie losowych dodatkowych krawędzi cofających
	i = 0
	rand.Seed(time.Now().UnixNano())
	for i < numberOfExtraReversedEdges {
		start := rand.Intn(numberOfVertices - 1)
		end := rand.Intn(numberOfVertices - 2)
		if start > end {
			v1 := vertices[start]
			v2 := vertices[end]
			if g.addEdge(v1, v2) {
				i++
			}
		}
	}
	return g
}

// dodanie krawędzi
func (g *Graph) addEdge(start *Vertex, end *Vertex) bool {

	// utworzenie kanału
	edge := [2]int{start.index, end.index}
	edgeChan := make(chan int)

	// sprawdzenie czy taka krawędź już istnieje
	found := false
	for _, e := range g.edges {
		if e == edge {
			found = true
		}
	}
	// zapisanie krawędzi
	if !found {
		g.edges = append(g.edges, edge)
		g.channels = append(g.channels, edgeChan)
		start.sendingChanels = append(start.sendingChanels, &edgeChan)
		end.receivingChanels = append(end.receivingChanels, &edgeChan)
	}

	return !found
}

// wyświetlenie połączeń grafu w prosty sposób
func (g Graph) show() {
	fmt.Println("Graf - ścieżki")
	for _, e := range g.edges {
		fmt.Printf("%d ───► %d\n", e[0], e[1])
	}
	fmt.Println()
}

// wyświetlenie grafu w ładny sposób w terminalu
func (g Graph) showPretty() {

	// pobranie wymiarów konsoli
	command := exec.Command("stty", "size")
	command.Stdin = os.Stdin
	out, err := command.Output()
	width := 77 // domyslna wartość małego okna terminalu
	if err == nil {
		out_s := strings.Replace(string(out), "\n", "", 1)
		width_s := strings.Split(out_s, " ")[1]
		width, _ = strconv.Atoi(width_s)
	}

	// przydatne symbole
	EMPTY_SIGN := "   "
	UP_ARROW := " ▲ "
	DOWN_ARROW := " ▼ "
	RIGHT_ARROW := "►  "
	LEFT_ARROW := "  ◄"
	VERTICAL_LINE := " │ "
	HORIZONTAL_LINE := "───"
	ANGLE_TOP_RIGHT := " └─"
	ANGLE_TOP_LEFT := "─┘ "
	ANGLE_BOTTOM_RIGHT := " ┌─"
	ANGLE_BOTTOM_LEFT := "─┐ "

	// stworzenie rozkładu
	w := width / 5
	h := width / 4
	cmd := make([][]string, h)
	for i := range cmd {
		cmd[i] = make([]string, w)
		for j := range cmd[i] {
			cmd[i][j] = EMPTY_SIGN
		}
	}

	// obliczenie odległości między punktami
	n := int(math.Floor(float64((2*w + 2*h - 4) / len(g.vertices))))

	// zaznaczenie punktów i narysowanie cyklu
	i := 0
	position := [2]int{0, int(n / 3)}
	direction := [2]int{0, 1}
	for i < len(g.vertices) {
		chr := EMPTY_SIGN
		if direction == [2]int{0, 1} {
			chr = RIGHT_ARROW
		} else if direction == [2]int{1, 0} {
			chr = DOWN_ARROW
		} else if direction == [2]int{0, -1} {
			chr = LEFT_ARROW
		} else if direction == [2]int{-1, 0} {
			chr = UP_ARROW
		}
		cmd[position[0]][position[1]] = chr
		if position[0]+direction[0] >= len(cmd) || position[0]+direction[0] < 0 || position[1]+direction[1] >= len(cmd[0]) || position[1]+direction[1] < 0 {
			// obróć kierunek o 90 stopni w lewo
			temp := direction[0]
			direction[0] = direction[1]
			direction[1] = -temp
		}
		position[0] += direction[0]
		position[1] += direction[1]
		cmd[position[0]][position[1]] = " " + strconv.Itoa((*(g.vertices[i])).index) + " "
		i++
		nn := 1
		for nn < n {
			if position[0]+direction[0] >= len(cmd) || position[0]+direction[0] < 0 || position[1]+direction[1] >= len(cmd[0]) || position[1]+direction[1] < 0 {
				// obróć kierunek o 90 stopni w lewo
				temp := direction[0]
				direction[0] = direction[1]
				direction[1] = -temp
				position[0] += direction[0]
				position[1] += direction[1]
			}
			position[0] += direction[0]
			position[1] += direction[1]
			nn++
		}
	}

	// narysowanie rogów i krawędzi
	if cmd[0][0] == EMPTY_SIGN {
		cmd[0][0] = ANGLE_BOTTOM_RIGHT
	}
	if cmd[0][w-1] == EMPTY_SIGN {
		cmd[0][w-1] = ANGLE_BOTTOM_LEFT
	}
	if cmd[h-1][0] == EMPTY_SIGN {
		cmd[h-1][0] = ANGLE_TOP_RIGHT
	}
	if cmd[h-1][w-1] == EMPTY_SIGN {
		cmd[h-1][w-1] = ANGLE_TOP_LEFT
	}
	for i := range cmd {
		if cmd[i][0] == EMPTY_SIGN {
			cmd[i][0] = VERTICAL_LINE
		}
		if cmd[i][w-1] == EMPTY_SIGN {
			cmd[i][w-1] = VERTICAL_LINE
		}
	}
	for i := range cmd[0] {
		if cmd[0][i] == EMPTY_SIGN {
			cmd[0][i] = HORIZONTAL_LINE
		}
		if cmd[h-1][i] == EMPTY_SIGN {
			cmd[h-1][i] = HORIZONTAL_LINE
		}
	}

	// narysowanie dodatkowych połączeń
	for _, v1 := range g.vertices {
		// znajdź współrzędne
		i1 := (*v1).index
		i1_x := -1
		i1_y := -1
		i1_s := " " + strconv.Itoa(i1) + " "
		for i := range cmd {
			for j := range cmd[i] {
				if cmd[i][j] == i1_s {
					i1_x = j
					i1_y = i
				}
			}
		}
		// znjadź sąsiadów
		neighbors := []int{}
		for _, e := range g.edges {
			if (e[0] == i1 && e[1] != i1+1) && (i1 != len(g.vertices)-1 || e[1] != 0) {
				neighbors = append(neighbors, e[1])
			}
		}
		if len(neighbors) > 0 {
			if i1_x == 0 {
				cmd[i1_y][1] = HORIZONTAL_LINE
				cmd[i1_y][2] = RIGHT_ARROW
				for i := 0; i < len(neighbors); i++ {
					cmd[i1_y][3+i] = " " + strconv.Itoa(neighbors[i]) + " "
				}
			} else if i1_x == len(cmd[0])-1 {
				cmd[i1_y][len(cmd[0])-2] = HORIZONTAL_LINE
				cmd[i1_y][len(cmd[0])-3] = LEFT_ARROW
				for i := 0; i < len(neighbors); i++ {
					cmd[i1_y][len(cmd[0])-4-i] = " " + strconv.Itoa(neighbors[i]) + " "
				}
			} else if i1_y == 0 {
				cmd[1][i1_x] = VERTICAL_LINE
				cmd[2][i1_x] = DOWN_ARROW
				for i := 0; i < len(neighbors); i++ {
					cmd[3+i][i1_x] = " " + strconv.Itoa(neighbors[i]) + " "
				}
			} else if i1_y == len(cmd)-1 {
				cmd[len(cmd)-2][i1_x] = VERTICAL_LINE
				cmd[len(cmd)-3][i1_x] = UP_ARROW
				for i := 0; i < len(neighbors); i++ {
					cmd[len(cmd)-4-i][i1_x] = " " + strconv.Itoa(neighbors[i]) + " "
				}
			}
		}
	}

	// wyświetlenie
	fmt.Println("Graf - wizualizacja")
	for _, row := range cmd {
		fmt.Printf(" ")
		for _, v := range row {
			fmt.Printf("%s", v)
		}
		fmt.Printf("\n")
	}
	fmt.Println()
}

// wyświetlenie podsumowania
func (g *Graph) summary() {
	fmt.Println()
	fmt.Println("Podumowanie")
	fmt.Println()
	for _, v := range g.vertices {
		fmt.Printf("Wierzchołek %d przekazał pakiety: %v\n", v.index, v.receivedPackets)
	}
	fmt.Println()
	for _, p := range g.receivedPackets {
		fmt.Printf("Pakiet %d szedł drogą: ", p)
		for _, v := range allPackets[p] {
			fmt.Printf("%d > ", v)
		}
		fmt.Printf("\n")
	}
}

/* ***********************************************************************
**************************************************************************
***************************  Program Logic  ******************************
**************************************************************************
*************************************************************************/

var wg sync.WaitGroup
var allPackets = make(map[int][]int)

var NUMBER_OF_VERTICES = 8             // n
var NUMBER_OF_EXTRA_EDGES = 3          // d
var NUMBER_OF_EXTRA_REVERSED_EDGES = 3 // b
var NUMBER_OF_PACKETS = 5              // k
var PACKET_LIFETIME = 11               // h

var receivedPackets = 0

func main() {
	// stwórz graf
	graph := GenerateGraph(NUMBER_OF_VERTICES, NUMBER_OF_EXTRA_EDGES, NUMBER_OF_EXTRA_REVERSED_EDGES)

	// pokaż graf
	graph.show()
	graph.showPretty()

	// uruchom wątki
	wg.Add(2)
	go graph.sender(NUMBER_OF_PACKETS)
	receivingChannel := make(chan int)
	go graph.receiver(&receivingChannel)
	for _, v := range graph.vertices {
		go v.move_sender(receivingChannel)
		go v.move_receiver()
	}
	wg.Wait()

	// wyświetl podsumowanie
	graph.summary()
}

// kod wątku nadawcy
func (g *Graph) sender(k int) {
	sendingChannel := make(chan int)
	g.channels = append(g.channels, sendingChannel)
	g.vertices[0].receivingChanels = append(g.vertices[0].receivingChanels, &sendingChannel)

	rand.Seed(time.Now().UnixNano())
	i := 0
	for i < k {
		time.Sleep(time.Duration(rand.Float64() * float64(time.Second)))
		if g.send(&sendingChannel) {
			i++
		}
	}
	defer wg.Done()
}

// wysłanie pierwszego pakietu do grafu
func (g *Graph) send(c *chan int) bool {
	// pobranie numeru pakietu
	packet := g.packetNumber
	g.packetNumber++

	// sprawdzenie czy graf istnieje
	if len(g.vertices) == 0 {
		fmt.Printf("Błąd: Nie można wysłać pakietu %d, ponieważ nie istnieje wierzchołek źródła\n", packet)
		return false
	}

	// wysłanie do grafu
	*c <- packet
	fmt.Printf("[Nadawca] \t\tWysłano pakiet %d\n", packet)
	return true
}

// kod wątku odbiorcy
func (g *Graph) receiver(receivingChannel *chan int) {
	g.channels = append(g.channels, *receivingChannel)
	g.vertices[len(g.vertices)-1].sendingChanels = append(g.vertices[len(g.vertices)-1].sendingChanels, receivingChannel)

	rand.Seed(time.Now().UnixNano())
	for receivedPackets < NUMBER_OF_PACKETS {
		time.Sleep(time.Duration(rand.Float64() * float64(time.Second)))
		g.receive(receivingChannel)
	}
	defer wg.Done()
}

// odebranie pakietu z ostatniego wierzchołka grafu
func (g *Graph) receive(c *chan int) bool {
	// sprawdzenie czy graf istnieje
	if len(g.vertices) == 0 {
		fmt.Printf("Błąd: Nie można odebrać pakietu, ponieważ nie istnieje wierzchołek ujścia\n")
		return false
	}

	if receivedPackets < NUMBER_OF_PACKETS {
		// pobranie pakietu
		packet := <-*c

		// dodanie pakietu do listy otrzymanych
		if packet != -1 {
			fmt.Printf("[Odbiorca] \t\tOtrzymano pakiet %d\n", packet)
			g.receivedPackets = append(g.receivedPackets, packet)
			receivedPackets++
		}
	}
	return true
}

// kody wątku wierzchołka
func (v *Vertex) move_receiver() {
	rand.Seed(time.Now().UnixNano())
	for {
		time.Sleep(time.Duration(rand.Float64() * float64(time.Second)))
		v.receive()
	}
}
func (v *Vertex) move_sender(receivingChannel chan int) {
	rand.Seed(time.Now().UnixNano())
	for {
		time.Sleep(time.Duration(rand.Float64() * float64(time.Second)))
		v.send(&receivingChannel)
	}
}

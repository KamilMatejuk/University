with Ada.Text_IO;               use Ada.Text_IO;
with Ada.Numerics.Float_Random; use Ada.Numerics.Float_Random;
with Ada.Containers.Vectors; use Ada.Containers;

procedure zad1v2 is

   NUMBER_OF_VERTICES    : Integer := 8; --n
   NUMBER_OF_EXTRA_EDGES : Integer := 5; --d
   NUMBER_OF_PACKETS     : Integer := 5; --k
   DELAY_TIME            : Float   := 2.0; -- seconds

   Seed : Generator;

   -- defining array of vectors
   package Integer_Vectors is new Ada.Containers.Vectors (Natural, Integer);
   use Integer_Vectors;
   subtype Integer_Vector is Integer_Vectors.Vector;
   type Array_Of_Vectors is array (Natural range <>) of Integer_Vector;
   type Array_Of_Integer is array (Natural range <>) of Integer;

   -- containing vector of neighbours for each vertex
   edges : Array_Of_Vectors (1 .. NUMBER_OF_VERTICES);
   -- containing vector of received packets for each vertex
   received_packets : Array_Of_Vectors (1 .. NUMBER_OF_VERTICES);
   -- containing current packet for each vertex
   current_packets : Array_Of_Integer (1 .. NUMBER_OF_VERTICES) :=
     (others => 0);
   -- containing vector of visited vertices for each packet
   visited_vertices : Array_Of_Vectors (1 .. NUMBER_OF_PACKETS);

   -- thread for moving packet through edge
   task type Vertex is
      entry set (set_index : Integer);
      entry finish;
   end Vertex;
   task body Vertex is
      index        : Integer;
      delay_value : Duration;
      to : Integer;
   begin
      loop
         select
            -- set values
            accept set (set_index : Integer) do
               index := set_index;
               delay_value := Duration(Random (Seed) * DELAY_TIME);
            end set;
            -- logic loop
            loop
               if index /= NUMBER_OF_VERTICES then
                  to := 0 + Integer(Random (Seed) * Float(edges(index).Length - 1));
                  to := edges(index)(to);
                  if current_packets (index) /= 0 and current_packets (to) = 0 then
                        current_packets (to)   := current_packets (index);
                        current_packets (index) := 0;
                        if received_packets (to).Find_Index (current_packets (to)) = -1 then
                           received_packets (to).Append (current_packets (to));
                        end if;
                        visited_vertices (current_packets (to)).Append (to);
                        Put_Line
                        ("[Wierzchołek " & to'Img & "] Przechowuje pakiet" &
                           current_packets (to)'Img);
                  end if;
                  delay delay_value;
               end if;
            end loop;
         or
            -- end task
            accept finish;
            exit;
         or
            -- end task
            terminate;
         end select;
      end loop;
   end Vertex;

   type Vertex_Ptr is access Vertex;
   vertex_tasks : array
     (1 .. NUMBER_OF_VERTICES) of Vertex_Ptr :=
     (others => new Vertex);

   -- thread for sending packet into graph
   task Sender is
      entry send;
   end Sender;
   task body Sender is
      delay_value : Duration;
   begin
      loop
         select
            -- start
            accept send do
               delay_value := Duration(Random (Seed) * DELAY_TIME);
            end send;
            -- logic loop
            for packet in 1 .. NUMBER_OF_PACKETS loop
               while current_packets (1) /= 0 loop
                  delay delay_value;
               end loop;
               current_packets (1) := packet;
               Put_Line ("[Nadawca] Wysłano pakiet" & packet'Img);
               delay delay_value;
            end loop;
            exit;
         or
            -- end task
            terminate;
         end select;
      end loop;
   end Sender;

   -- thread for recieving packet from graph
   task Reciever is
      entry recieve;
   end Reciever;
   task body Reciever is
      delay_value : Duration;
   begin
      loop
         select
            -- start
            accept recieve do
               delay_value := Duration(Random (Seed) * DELAY_TIME);
            end recieve;
            -- logic loop
            for packet in 1 .. NUMBER_OF_PACKETS loop
               while current_packets (NUMBER_OF_VERTICES) = 0 loop
                  delay delay_value;
               end loop;
               Put_Line
                 ("[Odbiorca] Otrzymano pakiet" &
                  current_packets (NUMBER_OF_VERTICES)'Img);
               current_packets (NUMBER_OF_VERTICES) := 0;
               delay delay_value;
            end loop;
            exit;
         or
            -- end task
            terminate;
         end select;
      end loop;
   end Reciever;

   -- other
   i1, i2, i3 : Integer;

begin
   -- --------------------------------------------------------------------
   -- -------------------------- graph creation --------------------------
   -- --------------------------------------------------------------------

   Put_Line ("Edges:");
   -- create loop edges
   for i in Natural range 1 .. NUMBER_OF_VERTICES - 1 loop
      i1 := i + 1;
      edges (i).Append (i + 1);
      Put_Line (i'Img & " -> " & i1'Img);
   end loop;
   -- create random extra edges
   i1 := 0;
   while i1 < NUMBER_OF_EXTRA_EDGES loop
      i2 := 1 + Integer (Random (Seed) * Float (NUMBER_OF_VERTICES - 1));
      i3 := 1 + Integer (Random (Seed) * Float (NUMBER_OF_VERTICES - 1));
      if i2 /= i3 and edges (i2).Find_Index (i3) = -1 then
         edges (i2).Append (i3);
         Put_Line (i2'Img & " -> " & i3'Img);
         i1 := i1 + 1;
      end if;
   end loop;

   -- start threads for each vertex
   for i in Natural range 1 .. NUMBER_OF_VERTICES loop
         vertex_tasks(i).set (i);
      end loop;
   -- start thread for sending
   Sender.send;
   -- start thread for receiving
   Reciever.recieve;

   -- wait for all packets to be recieved
   while not Reciever'Terminated loop
      delay 1.0;
   end loop;
   -- for e of edges_tasks loop
   --    e.finish;
   -- end loop;

   -- show results
   Put_Line ("");
   Put_Line ("Podumowanie");
   for i in Natural range 1 .. NUMBER_OF_VERTICES loop
      Put ("Wierzchołek " & i'Img & " przekazał pakiety: ");
      for j of received_packets (i) loop
         Put (j'Img & " ");
      end loop;
      Put_Line ("");
   end loop;
   for i in Natural range 1 .. NUMBER_OF_PACKETS loop
      Put ("Pakiet " & i'Img & " szedł drogą: ");
      for j of visited_vertices (i) loop
         Put (j'Img & " >");
      end loop;
      Put_Line ("");
   end loop;
   
end zad1v2;

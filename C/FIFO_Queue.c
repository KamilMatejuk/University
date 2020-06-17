/* Kamil Matejuk */
#include <stdio.h> 
#include <stdlib.h>
  
struct Queue { 
    int head; //stąd się zabiera elementy
    int tail; //tu się dodaje elementy
    int curr_size;
    int max_size; 
    int* values; 
}; 

struct Queue* createQueue(int max_size){ 
    struct Queue* queue = (struct Queue*) malloc(sizeof(struct Queue)); 
    queue->max_size = max_size;
    queue->curr_size = 0;
    queue->head = 0;
    queue->tail = -1;
    queue->values = (int*) malloc(queue->max_size * sizeof(int)); 
    return queue; 
} 

void enqueue(struct Queue* queue, int value){
	if(queue->curr_size < queue->max_size){ 
		queue->tail = (queue->tail + 1)%queue->max_size;
	    queue->values[queue->tail] = value; 
	    queue->curr_size = queue->curr_size + 1;
	    printf("%d enqueued to queue\n", value);
	} else {
		printf("Not enought space in queue to add %d\n", value);
	}
    
}

int dequeue(struct Queue* queue){ 
    if(queue->curr_size > 0){
    	int item = queue->values[queue->head]; 
	    queue->head = (queue->head + 1)%queue->max_size; 
	    queue->curr_size = queue->curr_size - 1; 
	    printf("%d dequeued from queue\n", item);
    } else {
    	return -1;
    }
} 

void showQueue(struct Queue* queue){
	int h = queue->head;
	int s = queue->max_size;
	int i = 0;
	printf("\nQueue contains of: ");
	while(i < queue->curr_size){
		int j = (h+i)%s;
		printf("%d ", queue->values[j]);
		i++;
	}
	printf("\n");
}

int main() { 
    struct Queue* queue = createQueue(4); 
  
    enqueue(queue, 10); 
    enqueue(queue, 20); 
    enqueue(queue, 30); 
    enqueue(queue, 40);
    enqueue(queue, 50);
    dequeue(queue);
    enqueue(queue, 60);

    showQueue(queue);
  
    return 0; 
} 
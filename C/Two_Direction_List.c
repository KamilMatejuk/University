/* Kamil Matejuk */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>


struct TwoDirectionCyclicList {
	struct ListNode* first;
};

struct ListNode {
	int value;
	struct ListNode* prev;
	struct ListNode* next;
};

void addNode(struct TwoDirectionCyclicList* list, int value){
	struct ListNode* node = (struct ListNode*)(malloc(sizeof(struct ListNode)));
	node->value = value;
	node->prev = NULL;
	node->next = NULL;

	if(list->first == NULL){
		list->first = node;
		node->next = node;
		node->prev = node;
	} else {
		node->next = list->first;
		node->prev = list->first->prev;
		list->first->prev->next = node;
		list->first->prev = node;

	}
	// printf("Added %d to list\n", value);
}

void deleteNode(struct TwoDirectionCyclicList* list, int value){
	struct ListNode* node = list->first;
	do {
		if(node->value == value){
			if(node == list->first){
				list->first = list->first->next;
			}
			node->prev->next = node->next;
			node->next->prev = node->prev;
			// printf("Deleted %d from list\n", value);
			break;
		}
		node = node->next;
	} while(node != list->first);
}

void showList(struct TwoDirectionCyclicList* list){
	printf("List contains of: ");
	struct ListNode* node = list->first;
	do {
		printf("%d ", node->value);
		node = node->next;
	} while(node != list->first);
	printf("\n");
}

struct TwoDirectionCyclicList* merge(struct TwoDirectionCyclicList* list1, struct TwoDirectionCyclicList* list2){
	if(list1 == NULL || list2 == NULL){
		return (struct TwoDirectionCyclicList*)(malloc(sizeof(struct TwoDirectionCyclicList)));
	} else {
		list1->first->prev->next = list2->first;
		list1->first->prev = list2->first->prev;
		list2->first->prev->next = list1->first;
		list2->first->prev = list1->first->prev;
		return list1;
	}
}

void findElement(struct TwoDirectionCyclicList* list, int value){
	struct ListNode* node = list->first;
	do {
		if(node->value == value){
			break;
		}
		node = node->next;
	} while(node != list->first);
}

void checkListImplementation(){
	printf("Checking list implementation...\n");
	struct TwoDirectionCyclicList* list = (struct TwoDirectionCyclicList*)(malloc(sizeof(struct TwoDirectionCyclicList)));
	list->first = NULL;
	addNode(list,10);
	addNode(list,20);
	addNode(list,30);
	addNode(list,40);
	deleteNode(list, 30);
	showList(list);
}

void checkMerge(){
	printf("Checking list merging...\n");
	struct TwoDirectionCyclicList* list1 = (struct TwoDirectionCyclicList*)(malloc(sizeof(struct TwoDirectionCyclicList)));
	list1->first = NULL;
	addNode(list1,10);
	addNode(list1,20);
	addNode(list1,30);
	showList(list1);

	struct TwoDirectionCyclicList* list2 = (struct TwoDirectionCyclicList*)(malloc(sizeof(struct TwoDirectionCyclicList)));
	list2->first = NULL;
	addNode(list2,40);
	addNode(list2,50);
	addNode(list2,60);
	showList(list2);

	struct TwoDirectionCyclicList* mergedList = merge(list1,list2);
	showList(mergedList);
}

void checkTimingOne(){
	struct TwoDirectionCyclicList* list = (struct TwoDirectionCyclicList*)(malloc(sizeof(struct TwoDirectionCyclicList)));
	list->first = NULL;
	srand(time(NULL));
	int numberToCheck = rand()%1000;
	for(int i=0; i<1000; i++){
		int random = rand()%1000;
		addNode(list,random);

		if(i == numberToCheck){
			numberToCheck = random;
		}
	}

	double summaryTime = 0;
	for(int i=0; i<100; i++){
		clock_t t = clock(); 
    	findElement(list,numberToCheck);
    	t = clock() - t; 
    	summaryTime += ((double)t)/CLOCKS_PER_SEC;
	}
	printf("Finding one same value %d took on average %f seconds\n", numberToCheck, summaryTime/100.0);
}

void checkTimingManyRandom(){
	struct TwoDirectionCyclicList* list = (struct TwoDirectionCyclicList*)(malloc(sizeof(struct TwoDirectionCyclicList)));
	list->first = NULL;
	srand(time(NULL));
	int numbersToCheck[100];
	for(int i=0; i<100; i++){
		int number = rand()%100*(i+1);
		numbersToCheck[i] = number;
	}

	int j=0;
	for(int i=0; i<1000; i++){
		int random = rand()%1000;
		addNode(list,random);

		if(i == numbersToCheck[j]){
			numbersToCheck[j] = random;
			j++;
		}
	}

	double summaryTime = 0;
	for(int i=0; i<100; i++){
		clock_t t = clock(); 
    	findElement(list,numbersToCheck[i]);
    	t = clock() - t; 
    	summaryTime += ((double)t)/CLOCKS_PER_SEC;
	}
	printf("Finding multiple random values took on average %f seconds\n", summaryTime/100.0);

}

int main(){
	checkListImplementation();
	checkMerge();
	checkTimingOne();
	checkTimingManyRandom();

	return 0;
}
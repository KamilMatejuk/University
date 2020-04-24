/* Kamil Matejuk */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* Zaimplementuj jednokierunkowa liste. Dodaj do listy 1000 losowych elementów. Zmierz średni czas dostepu 
do tego samego i losowego elementu. Wytłumacz róznice. Zaimplementuj funkcje megre(lista l1, lista l2) łączaca 2 listy. */

struct OneDirectionList {
	struct ListNode* first;
	struct ListNode* last;
};

struct ListNode {
	int value;
	struct ListNode* next;
};

void addNode(struct OneDirectionList* list, int value){
	struct ListNode* node = (struct ListNode*)(malloc(sizeof(struct ListNode)));
	node->value = value;
	node->next = NULL;

	if(list->first == NULL){
		list->first = node;
		list->last = node;
	} else {
		list->last->next = node;
		list->last = node;
	}
	// printf("Added %d to list\n", value);
}

void deleteNode(struct OneDirectionList* list, int value){
	struct ListNode* node = list->first;
	struct ListNode* prev_node = NULL;
	while(node != NULL){
		if(node->value == value){
			if(prev_node == list->first){
				list->first = node->next;
			} else {
				prev_node->next = node->next;
			}
			// printf("Deleted %d from list\n", value);
			break;
		}
		prev_node = node;
		node = node->next;
	}
}

void showList(struct OneDirectionList* list){
	printf("List contains of: ");
	struct ListNode* node = list->first;
	while(node != NULL){
		printf("%d ", node->value);
		node = node->next;
	}
	printf("\n");
}

struct OneDirectionList* merge(struct OneDirectionList* list1, struct OneDirectionList* list2){
	if(list1 == NULL || list2 == NULL){
		return (struct OneDirectionList*)(malloc(sizeof(struct OneDirectionList)));
	} else {
		list1->last->next = list2->first;
		return list1;
	}
}

void findElement(struct OneDirectionList* list, int value){
	struct ListNode* node = list->first;
	while(node != NULL){
		if(node->value == value){
			break;
		}
		node = node->next;
	}
}

void checkListImplementation(){
	printf("Checking list implementation...\n");
	struct OneDirectionList* list = (struct OneDirectionList*)(malloc(sizeof(struct OneDirectionList)));
	list->first = NULL;
	list->last = NULL;
	addNode(list,10);
	addNode(list,20);
	addNode(list,30);
	addNode(list,40);
	deleteNode(list, 30);
	showList(list);
}

void checkMerge(){
	printf("Checking list merging...\n");
	struct OneDirectionList* list1 = (struct OneDirectionList*)(malloc(sizeof(struct OneDirectionList)));
	list1->first = NULL;
	list1->last = NULL;
	addNode(list1,10);
	addNode(list1,20);
	addNode(list1,30);
	showList(list1);

	struct OneDirectionList* list2 = (struct OneDirectionList*)(malloc(sizeof(struct OneDirectionList)));
	list2->first = NULL;
	list2->last = NULL;
	addNode(list2,40);
	addNode(list2,50);
	addNode(list2,60);
	showList(list2);

	struct OneDirectionList* mergedList = merge(list1,list2);
	showList(mergedList);
}

void checkTimingOne(){
	struct OneDirectionList* list = (struct OneDirectionList*)(malloc(sizeof(struct OneDirectionList)));
	list->first = NULL;
	list->last = NULL;
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
	struct OneDirectionList* list = (struct OneDirectionList*)(malloc(sizeof(struct OneDirectionList)));
	list->first = NULL;
	list->last = NULL;
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
#include <stdio.h>
#include <stdlib.h>

int comparison = 0;

// Creating structure [Number|PointerToNextNumber] -> [Number|PointerToNextNumber] -> [Number|PointerToNextNumber] -> etc.
//                            <head>               ->       one node further       ->       one node further       -> ...
typedef struct node {
	int number;
	struct node * next;
} node;

void showLinkedList(node * head);
int isEmpty(node * head);
node * addNumber(node * head, int num);
node * deleteNumber(node * head, int num);
node * findMTF(node * head, int num);
node * findRMTF(node * head, int num);
node * findTRANS(node * head, int num);
node * findRTRANS(node * head, int num);
node * delmax1(node * head);
//node * delmax2(node * head);

int main(){
	
	// Startup with instructions and explanation
    printf("- [Linked List in C] - \n");
	printf("Options:\n");
	printf("insert [num] - Inserts a number to the list\n");
	printf("delete [num] - Deletes a number from the list if exists\n");
	printf("findMTF [num] - Looks for a number. If exists - moves it to the top.\n");
	printf("findRMTF [num] - Looks for a number. If exists - moves it to the bottom.\n");
	printf("findTRA [num] - Looks for a number. If exists - moves it up one space.\n");
	printf("findRTRA [num] - Looks for a number. If exists - moves it up one space.\n");
	printf("random [num] - Shuffles num range <1, ..., 100> and switches them X times.\n");
	printf("delmax1 - Sorts by using findMTF and deletes linked list afterwards.\n");
	printf("delmax2 - Sorts by using findTRA and deletes linked list afterwards.\n");
	printf("isempty - Checks if list is empty\n");
	printf("show - Displays list.\n");
	printf("exit - Exits the program.\n\n");

	// Creating head of Linked List
	node * HEAD = NULL;
	
	// Checking user input (option)
	char option[8];
	while(1){
		printf("> Choose option: ");
		scanf("%s", option);
		
		int num = 0;
		if(strcmp(option,"insert") == 0){
			scanf("%d", &num);
			HEAD = addNumber(HEAD, num);
			showLinkedList(HEAD);
		}else if(strcmp(option,"delete") == 0){
			scanf("%d", &num);
			HEAD = deleteNumber(HEAD, num);
			showLinkedList(HEAD);
		}else if(strcmp(option,"findMTF") == 0){
			scanf("%d", &num);
			HEAD = findMTF(HEAD, num);
			showLinkedList(HEAD);
		}else if(strcmp(option,"findRMTF") == 0){
			scanf("%d", &num);
			HEAD = findRMTF(HEAD, num);
			showLinkedList(HEAD);
		}else if(strcmp(option,"findTRA") == 0){
			scanf("%d", &num);
			HEAD = findTRANS(HEAD, num);
			showLinkedList(HEAD);
		}else if(strcmp(option,"findRTRA") == 0){
			scanf("%d", &num);
			HEAD = findRTRANS(HEAD, num);
			showLinkedList(HEAD);
		}else if(strcmp(option,"isempty") == 0){
			isEmpty(HEAD);
		}else if(strcmp(option,"show") == 0){
			showLinkedList(HEAD);
		}else if(strcmp(option,"exit") == 0){
			return 0;
		}else if(strcmp(option,"random") == 0){
			scanf("%d", &num);
			int i = 100;
			for(i=100; i>=1; i--){
				HEAD = addNumber(HEAD, i);
			}
			
			// Shuffling them (num is number of shuffles)
			int s = 0;
			for(s=1; s<=num; s++){
				HEAD = findTRANS(HEAD, (rand()%100)+1);
			}
		}else if(strcmp(option,"delmax1") == 0){
			
			comparison = 0;
			node * p = HEAD;
	
			int k = 100;
			for(k=100; k>=1; k--){
				HEAD = findRMTF(HEAD, k);
			}
			for(k=100; k>=1; k--){
				HEAD = deleteNumber(HEAD, k);
			}
			
			isEmpty(HEAD);
			printf("Comparisions in functions: %d\n", comparison);
			
		}else if(strcmp(option,"delmax2") == 0){
			
			//HEAD = delmax2(HEAD);
			
			comparison = 0;
			int testAgain = 1;
			node * p = HEAD;
				
			while(testAgain == 1){
				testAgain = 0;
				while(p->next != NULL){
					comparison += 2;
					if(p->number < p->next->number){
						HEAD = findTRANS(HEAD, p->number);
						testAgain = 1;
					}
					p = p->next;
				}
			}
			
			int k = 100;
			for(k=100; k>=1; k--){
				HEAD = deleteNumber(HEAD, k);
			}
			
			isEmpty(HEAD);
			printf("Comparisions in functions: %d\n", comparison);
			
		}else{
			printf("Check spelling.\n");
		}
	}
    return 0;
}

node * addNumber(node * head, int num){
	node * temp = NULL;
	node * p = NULL;
	
	// Creating temporary node, independent from the Linked List atm.
	temp = (node*)malloc(sizeof(node));
	temp->number = num;
	temp->next = NULL;
	
	if(head == NULL){
		head = temp;
	}else{
		p = head;
		while(p->next != NULL){
			p = p->next;
		}
		p->next = temp;
	}
	
	return head;
}

node * deleteNumber(node * head, int num){
	node * temp = NULL;
	node * p = head;

	// Searching for number declared by user
	while(p->number != num){
		comparison += 2;
		
		// If pointer went to null it means that we searched trough whole list already. 
		// Quiting w/o action.
		if(p->next == NULL){
			printf("Number wasn't found in the Linked List.\n");
			return head;
		}
		
		// Temp is saving pos for previous node for: [$]
		temp = p;
		p = p->next;
	}
	printf("Found number %d in Linked List. Deleting.\n", p->number);
	
	// If we never entered the while, then it means the number we look for is head of linked list.
	// It the temp is still NULL.
	comparison++;
	if(temp == NULL){
		head = p->next;
		return head;
	}
	
	// [$]: Previous one is now pointing two nodes further.
	temp->next = temp->next->next;
	
	return head;
}

node * findRMTF(node * head, int num){
	node * temp = NULL;
	node * p = head;

	while(p->number != num){
		comparison += 2;
		
		if(p->next == NULL){
			printf("Number %d wasn't found in the Linked List.\n", num);
			return head;
		}
		
		temp = p;
		p = p->next;
	}
	printf("Found number %d in Linked List. Moving upwards max.\n", p->number);
	
	// Finding last node and linking it to the found number.
	node * last = head;
	while(last->next != NULL){
		comparison++;
		last = last->next;
	}
	
	comparison++;
	if(temp == NULL){
		last->next = head;
		head = p->next;
		p->next = NULL;
		
		return head;
	}
	
	
	last->next = temp->next;
	temp->next = temp->next->next;
	
	// And the found number shouldn't make weird cycle with it's pointer somewhere back in the list. Seting it to null.
	p->next = NULL;
	
	return head;
}

node * findTRANS(node * head, int num){
	node * temp = NULL;
	node * p = head;

	while(p->number != num){
		comparison += 2;
		
		if(p->next == NULL){
			printf("Number %d wasn't found in the Linked List.\n", num);
			return head;
		}
		
		temp = p;
		p = p->next;
	}
	printf("Found number %d in Linked List. Moving one up.\n", p->number);
		
	// First if checks if anything is above called number.	
	comparison++;
	if(p->next != NULL){
		
		comparison++;
		if(temp == NULL){
			p = p->next;
			head->number = p->number;
			p->number = num;
			
			return head;
		}
			
		// Moving pointer one place further to get value to swap
		p = p->next;
		temp->next->number = p->number;
		p->number = num;
	}else{
		printf("This element is already as far as possible.\n");
	}
			
	return head;
}

void showLinkedList(node * head){
	node * p = head;
	while(p != NULL){
		printf("%d -> \t", p->number);
		p = p->next;
	}
	printf("\n");
}

int isEmpty(node * head){
	node * p = head;
	if(p == NULL){
		printf("Linked List is empty.\n");
		return 0;
	}else{
		printf("Linked List is filled.\n");
		return 1;
	}
}

// Probably misunderstood the task, doing extra functions to compensate

node * findMTF(node * head, int num){
	node * temp = NULL;
	node * p = head;

	while(p->number != num){
		comparison += 2;
		
		if(p->next == NULL){
			printf("Number %d wasn't found in the Linked List.\n", num);
			return head;
		}
		
		temp = p;
		p = p->next;
	}
	printf("Found number %d in Linked List. Moving upwards max.\n", p->number);
	
	comparison++;
	if(temp == NULL){
		printf("That number is already on the bottom of the list.\n");
		return head;
	}
	
	if(p->next == NULL){
		// Linking one behind found number two nodes further to skip found node
		temp->next = NULL;
	}else{
		// Linking one behind found number two nodes further to skip found node
		temp->next = temp->next->next;
	}
	// Debug pre
	printf("p->num: %d, p->next->num: %d, head->num: %d, temp->num: %d, temp->next->num %d\n", p->number, p->next->number, head->number, temp->number, temp->next->number);

	// Linking node after found number to head.
	p->next = head;
	// New head is found node
	head = p;
	
	// Debug aft
	printf("p->num: %d, p->next->num: %d, head->num: %d, temp->num: %d, temp->next->num %d\n", p->number, p->next->number, head->number, temp->number, temp->next->number);
	
	return head;
}

node * findRTRANS(node * head, int num){
	node * temp = NULL;
	node * p = head;

	while(p->number != num){
		comparison += 2;
		
		if(p->next == NULL){
			printf("Number %d wasn't found in the Linked List.\n", num);
			return head;
		}
		
		temp = p;
		p = p->next;
	}
	printf("Found number %d in Linked List. Moving one up.\n", p->number);
		
	// First if checks if anything is above called number.	
	comparison++;
	if(p->next != NULL){
		
		comparison++;
		if(temp == NULL){
			p = p->next;
			head->number = p->number;
			p->number = num;
			
			return head;
		}
			
		// Moving pointer one place further to get value to swap
		p = p->next;
		temp->next->number = p->number;
		p->number = num;
	}else{
		printf("This element is already as far as possible.\n");
	}
			
	return head;
}

// Easy Debugger
// printf("p->num: %d, p->next->num: %d, head->num: %d, temp->num: %d, temp->next->num %d\n", p->number, p->next->number, head->number, temp->number, temp->next->number);



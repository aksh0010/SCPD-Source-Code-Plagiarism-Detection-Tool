#include <stdio.h>
/*
Code for factorial 
*/
int main()
{
  printf("Enter a number to loop \n");
  int num;
  scanf("%d", &num);

  if (num > 0)
  {

    for (int count = 0; count < num; count++)
    {
      printf("%d\n", count);
    }
    printf("Bye..\n");
  }
  

  return 0;
}
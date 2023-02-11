#include <stdio.h>

int main()
{
  int c, n, f = 1;
// helloo my name is this 
  printf("Enter a number to calculate its factorial\n");
  scanf("%d", &n);
  for (c = 1; c <= n; c++)
    f = f * c;
/*
helllo
*/
  printf("Factorial of %d = %d\n", n, f);

  return 0;
}
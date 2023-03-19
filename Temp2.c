#include <stdio.h>

int main()
{
  int c, n, f = 1;
  printf("Enter a number to calculate its factorial\n");
  scanf("%d", &n);
  for (c = 1; c <= n; c++)
    f = f * c;
  printf("Factorial of %d = %d\n", n, f);

  switch (f)
  {
  case 10:
    printf("Hello boy\n");
    break;

  default:
    printf("Hello girl\n");
    break;
  }
  return 0;
}
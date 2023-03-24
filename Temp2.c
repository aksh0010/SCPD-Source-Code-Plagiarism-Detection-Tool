#include <stdio.h>

// Recursive function to compute the nth Fibonacci number
int fibonacci(int n)
{
  return (n <= 1) ? n : fibonacci(n - 1) + fibonacci(n - 2);
}

int main()
{
  int n = 2, max_num = 300;
  while (1)
  {
    int next_num = fibonacci(n);
    if (next_num >= max_num)
    {
      break;
    }

    // Check if the next_num is a prime number
    int is_prime = (next_num < 1) ? 0 : 1;
    int i = 2;
    while (i < next_num && is_prime)
    {
      is_prime = (next_num % i != 0) ? 1 : 0;
      i++;
    }

    if (is_prime)
    {
      printf("%d ", next_num);
    }

    n++;
  }

  return 0;
}
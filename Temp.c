#include <stdio.h>
#define max_num 500

int main()
{
  int temp = 0;
  int temp2 = 1;
  int next_num, i = 2;
  do
  {
    next_num = temp + temp2;
    temp = temp2;
    temp2 = next_num;
    i++;

    // Check if the next_num is a prime number and less than max_num
    int is_prime = 1;
    for (int j = 2; j < next_num; j++)
    {
      if (next_num % j == 0)
      {
        is_prime = 0;
        break;
      }
    }

    // Print the Fibonacci number if it is a prime number and less than max_num
    if (is_prime && next_num < max_num)
    {
      printf("%d ", next_num);
    }
  } while (next_num < max_num);

  return 0;
}
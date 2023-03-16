def dot_product(v1, v2):
    result = 0
    for key in v1.keys():
        if key in v2:
            result += v1[key] * v2[key]
    return result
h1 = {'a': 1, 'b': 2, 'c': 3}
h2 = {'a': 1, 'b': 2, 'c': 3}
result = (dot_product(h1, h2))*100
print(result)  # Output: 32

# print(dot_product(v1, v2))  # Output: 28

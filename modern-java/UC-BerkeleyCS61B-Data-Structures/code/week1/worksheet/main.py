def evens(list_of_ints):
    """Returns a copy of the list but keeping only the even numbers."""
    ret = []
    
    for idx in range(len(list_of_ints)):
        if (list_of_ints[idx] & 1) == 0:
            ret.append(list_of_ints[idx])
    return ret
            
def count_words(list_of_words):
    """Returns a map from each word to its count."""
    counts = {}
    for word in list_of_words:
        counts[word] = counts.get(word,0) + 1
    return counts

nums = [2, 5, 8, 7, 3, 10]
words = ["hello", "world", "hello", "cs61b", "world", "hello"]

expected_evens = [2, 8, 10]  
assert evens(nums) == expected_evens, f"evens 测试失败: 期望 {expected_evens}, 实际得到 {evens(nums)}"

expected_words = {'hello': 3, 'world': 2, 'cs61b': 1}
assert count_words(words) == expected_words, f"count_words 测试失败: 期望 {expected_words}, 实际得到 {count_words(words)}"

print("所有测试通过 ✅")
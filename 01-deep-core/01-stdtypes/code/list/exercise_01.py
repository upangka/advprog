l = [1, 2, 3, 5, 6]
pre_id = id(l)

l = [1, 2, 3, 5, 6, 7]
after_id = id(l)

assert pre_id != after_id, "new another list"

l[:] = [1, 2, 3, 5, 6]
change_id = id(l)

assert change_id == after_id, "Should the same"
assert l == [1, 2, 3, 5, 6]

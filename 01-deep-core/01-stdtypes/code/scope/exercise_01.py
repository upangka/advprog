globs = {"x": 7, "y": 10, "birds": ["Parrot", "Swallow", "Albatross"]}

locs = {}
exec("z = 3 * x + 4 * y", globs, locs)

print(locs)
print(globs.keys())

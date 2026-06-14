MISSING = object()
MSG = "max() arg is an empty sequence"


def mymax(first, *args, key=None, default=MISSING):
    if args:
        series = args
        candidate = first
    else:
        series = iter(first)
        try:
            candidate = next(series)
        except StopIteration:
            if default is not MISSING:
                return default
            raise ValueError

    if key:
        # assert callable(key),"Not callable"
        candidate_key = key(candidate)
        for current in series:
            current_key = key(current)
            if current_key > candidate_key:
                candidate_key = current_key
                candidate = current
    else:
        for current in series:
            if current > candidate:
                candidate = current
    return candidate


# mymax([2,3,5,-100],2)

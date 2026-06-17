class Result:
    def __init__(self,value=None,exc=None):
        # Use value for a result produced by "return"
        # Use exc for an exception produced by "raise"
        assert (value is None) or (exc is None)
        self._value = value
        self._exc = exc
        
    def unwrap(self):
        # Produce the enclosed result
        if self._exc:
            raise self._exc
        else:
            return self._value
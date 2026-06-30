def loud(cls):
    """monkey patching"""
    orig_noise = cls.noise

    def noise(self):
        return str.upper(orig_noise(self))

    cls.noise = noise

    orig_pedal = cls.pedal

    def pedal(self):
        return str.upper(orig_pedal(self))

    cls.pedal = pedal

    return cls


def annoying(cls):
    """monkey patching"""
    orig_noise = cls.noise

    def noise(self):
        return orig_noise(self) * 3

    cls.noise = noise

    orig_pedal = cls.pedal

    def pedal(self):
        return orig_pedal(self) * 3

    cls.pedal = pedal
    return cls


@annoying
@loud
class Cyclist:

    def noise(self):
        return "On your left!"

    def pedal(self):
        return "Pedaling"


"""interactive
$ uv run python -i exercise_09.py
>>> Cyclist().noise()
'ON YOUR LEFT!ON YOUR LEFT!ON YOUR LEFT!'
>>> Cyclist().pedal()
'PEDALINGPEDALINGPEDALING'
"""

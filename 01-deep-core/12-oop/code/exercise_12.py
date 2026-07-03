class Duck:

    def noise(self):
        return "Quack"

    def waddle(self):
        return "Waddle"


class TromBonist:
    def noise(self):
        return "Blat"

    def march(self):
        return "Clomp"


class Cyclist:
    def noise(self):
        return "On your left!"

    def pedal(self):
        return "Pedaling"


def dispatch_v1(obj):
    if isinstance(obj, Duck):
        handle_duck(obj)
    elif isinstance(obj, TromBonist):
        handle_trombonist(obj)
    elif isinstance(obj, Cyclist):
        handle_cyclist(obj)
    else:
        raise RuntimeError("Unknown object")

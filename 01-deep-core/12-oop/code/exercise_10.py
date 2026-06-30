class Base:
    def __init_subclass__(cls):
        print("Initializing", cls.__name__)


class A(Base):
    pass


class B(Base):
    pass

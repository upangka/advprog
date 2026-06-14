import pytest

@pytest.fixture
def fruits():
    return 'banana kiwi mango apple watermelon'.split()

def test_something(fruits):
    """fruits会自动注入"""
    print(fruits)
    assert len(fruits) == 5
    
    
from config import parse_integer,parse_name
 
def parse_setting(text,index):
    ...

def test_parse_setting():
    assert parse_setting("name=42;", 0) == (('name', 42), 8)
    assert parse_setting("x", 0) == None
    assert parse_setting("xyz 2", 0) == None    # Missing '='
    assert parse_setting("a=42", 0) == None    # Missing ';' at end
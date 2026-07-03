from collections import UserList

class ulist(UserList):
    
    def __setitem__(self, i, item):
        print(f"设置值{item}")
        super().__setitem__(i,item)
        
    def __delitem__(self, i) -> None:
        print(f"删除-> {i}")
        return super().__delitem__(i)
from project import is_valid_username, is_valid_password, register_user, login_user

def test_is_valid_username():
    assert is_valid_username("user") == True
    assert is_valid_username("us") == False
    assert is_valid_username("username") == False
    assert is_valid_username("USER") == False

def test_is_valid_password():
    assert is_valid_password("Pass1") == True
    assert is_valid_password("password") == False
    assert is_valid_password("PasswordTooLong1") == False
    assert is_valid_password("Short1") == True
    assert is_valid_password("NoNumbers") == False
    
def test_login_user_invalid_password():
    register_user("user1", "Pass1")
    assert login_user("user1", "WrongPass") == False
    
def test_login_nonexistent_user():
    assert login_user("nonexistent", "Pass1") == False
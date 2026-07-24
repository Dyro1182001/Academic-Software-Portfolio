import sys

class User:
    def __init__(self, name: str, password: str):
        self.name = name
        self.password = password

    def to_string(self) -> str:
        return f"{self.name},{self.password}"

    @staticmethod
    def from_string(user_string: str):
        name, password = user_string.strip().split(",")
        return User(name, password)


"""Username validate function"""
def is_valid_username(name: str) -> bool:
    return len(name) >= 3 and len(name) <= 7 and name.islower()


"""Password validate function"""
def is_valid_password(password: str) -> bool:
    if len(password) >= 11:
        return False
    if not any(char.isdigit() for char in password):
        return False
    if not any(char.isupper() for char in password):
        return False
    return True


"""Register function"""
def register_user(name: str, password: str):
    if not is_valid_username(name):
        print("Invalid username: must be 3 to 7 characters and all lowercase.")
        return
    if not is_valid_password(password):
        print("Invalid password: must be less than 11 characters, contain at least 1 number, and 1 capitalized letter.")
        return
    user = User(name, password)
    with open("users.txt", "a") as file:
        file.write(user.to_string() + "\n")
    print(f"User '{name}' registered successfully.")



"""Login function"""
def login_user(name: str, password: str) -> bool:
    try:
        with open("users.txt", "r") as file:
            for line in file:
                existing_user = User.from_string(line)
                if existing_user.name == name and existing_user.password == password:
                    print(f"Login successful for user '{name}'.")
                    return True
    except FileNotFoundError:
        print("No users found. Please register first.")
    print(f"Login failed for user '{name}'.")
    return False


"""Main function"""
def main(): 
    if len(sys.argv) != 2:
        print("You have to select wether to REGISTER or LOGIN")
        print("Usage: python project.py <action>")
        print("Actions: register, login")
        return

    action = sys.argv[1]

    if action in ["register", "login"]:
        name = input("Name: ").strip()
        password = input("Password: ").strip()

        if action == "register":
            register_user(name, password)
        elif action == "login":
            login_user(name, password)
    else:
        print("Invalid action. Use 'register' or 'login'.")

if __name__ == "__main__":
    main()
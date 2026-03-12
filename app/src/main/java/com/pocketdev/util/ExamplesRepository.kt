package com.pocketdev.util

import com.pocketdev.domain.model.CodeExample
import com.pocketdev.domain.model.DifficultyLevel
import com.pocketdev.domain.model.ExampleCategory
import com.pocketdev.domain.model.Language

/**
 * Repository of code examples for learning
 */
object ExamplesRepository {

    val allExamples: List<CodeExample> = listOf(
        // PYTHON EXAMPLES
        CodeExample(
            id = "py_001",
            title = "Hello World & Variables",
            description = "Learn how to print output and work with variables in Python",
            language = Language.PYTHON,
            category = ExampleCategory.BASICS,
            code = """# Hello World in Python
print("Hello, World!")

# Variables in Python
name = "Student"
age = 20
score = 95.5

print(f"My name is {name}")
print(f"I am {age} years old")
print(f"My score is {score}")

# Dynamic typing - Python figures out the type
x = 10      # integer
x = "text"  # now it's a string!
print(x)
""".trimIndent()
        ),
        CodeExample(
            id = "py_002",
            title = "Loops & Conditionals",
            description = "Master if statements, for loops, and while loops",
            language = Language.PYTHON,
            category = ExampleCategory.CONTROL_FLOW,
            code = """# Conditional Statements
age = 18

if age < 13:
    print("You're a child")
elif age < 20:
    print("You're a teenager")
else:
    print("You're an adult")

# For Loop
print("\nCounting from 1 to 5:")
for i in range(1, 6):
    print(i)

# While Loop
print("\nCountdown:")
count = 5
while count > 0:
    print(count)
    count -= 1
print("Blast off!")

# Loop with break and continue
print("\nEven numbers only:")
for num in range(1, 11):
    if num % 2 != 0:
        continue  # Skip odd numbers
    print(num)
""".trimIndent()
        ),
        CodeExample(
            id = "py_003",
            title = "Functions & Parameters",
            description = "Create reusable code with functions",
            language = Language.PYTHON,
            category = ExampleCategory.FUNCTIONS,
            code = """# Basic Function
def greet():
    print("Hello!")

greet()

# Function with Parameters
def greet_person(name):
    print(f"Hello, {name}!")

greet_person("Alice")
greet_person("Bob")

# Function with Return Value
def add(a, b):
    return a + b

result = add(5, 3)
print(f"5 + 3 = {result}")

# Function with Default Parameters
def introduce(name, age=18):
    print(f"{name} is {age} years old")

introduce("Charlie")
introduce("Diana", 25)

# Multiple Return Values
def get_stats(numbers):
    return min(numbers), max(numbers), sum(numbers) / len(numbers)

nums = [10, 20, 30, 40, 50]
minimum, maximum, average = get_stats(nums)
print(f"Min: {minimum}, Max: {maximum}, Avg: {average}")
""".trimIndent()
        ),
        CodeExample(
            id = "py_004",
            title = "Lists & Dictionaries",
            description = "Work with Python data structures",
            language = Language.PYTHON,
            category = ExampleCategory.DATA_STRUCTURES,
            code = """# Lists
fruits = ["apple", "banana", "cherry"]
print(fruits[0])  # apple
fruits.append("orange")
print(len(fruits))  # 4

# List comprehension
squares = [x**2 for x in range(5)]
print(squares)  # [0, 1, 4, 9, 16]

# Dictionaries
student = {
    "name": "Alice",
    "age": 20,
    "grade": "A"
}

print(student["name"])  # Alice
student["age"] = 21
student["city"] = "NYC"  # Add new key

# Iterate through dictionary
for key, value in student.items():
    print(f"{key}: {value}")

# Nested structures
school = {
    "students": [
        {"name": "Alice", "grade": "A"},
        {"name": "Bob", "grade": "B"}
    ]
}

print(school["students"][0]["name"])
""".trimIndent()
        ),
        CodeExample(
            id = "py_005",
            title = "Classes & Objects",
            description = "Introduction to Object-Oriented Programming",
            language = Language.PYTHON,
            category = ExampleCategory.OOP,
            code = """# Define a Class
class Dog:
    def __init__(self, name, age):
        self.name = name
        self.age = age
    
    def bark(self):
        return f"{self.name} says Woof!"
    
    def get_human_age(self):
        return self.age * 7

# Create objects
dog1 = Dog("Buddy", 3)
dog2 = Dog("Max", 5)

print(dog1.bark())  # Buddy says Woof!
print(f"{dog2.name}'s human age: {dog2.get_human_age()}")

# Inheritance
class Puppy(Dog):
    def __init__(self, name, age):
        super().__init__(name, age)
        self.is_trained = False
    
    def train(self):
        self.is_trained = True
        return f"{self.name} is now trained!"

puppy = Puppy("Rex", 1)
print(puppy.bark())
print(puppy.train())
""".trimIndent()
        ),

        // JAVASCRIPT EXAMPLES
        CodeExample(
            id = "js_001",
            title = "Variables & Data Types",
            description = "Learn about var, let, const and JavaScript data types",
            language = Language.JAVASCRIPT,
            category = ExampleCategory.BASICS,
            code = """// Variables in JavaScript
let name = "Student";
const age = 20;
var score = 95.5; // Older way (avoid in modern JS)

console.log("Hello, " + name);
console.log(`Age: ${age}`); // Template literal

// Data Types
let text = "Hello";      // String
let number = 42;         // Number
let isActive = true;     // Boolean
let nothing = null;      // Null
let undefinedVar;        // Undefined
let obj = { key: "value" }; // Object
let arr = [1, 2, 3];     // Array

console.log(typeof text); // "string"
console.log(typeof number); // "number"

// Type coercion
console.log("5" + 3); // "53" (string concatenation)
console.log("5" - 3); // 2 (numeric subtraction)
""".trimIndent()
        ),
        CodeExample(
            id = "js_002",
            title = "Functions & Arrow Functions",
            description = "Master regular and arrow function syntax",
            language = Language.JAVASCRIPT,
            category = ExampleCategory.FUNCTIONS,
            code = """// Regular Function
function greet(name) {
    return "Hello, " + name;
}

console.log(greet("Alice"));

// Function Expression
const add = function(a, b) {
    return a + b;
};

console.log(add(5, 3));

// Arrow Function (ES6)
const multiply = (a, b) => a * b;
console.log(multiply(4, 3));

// Arrow with multiple lines
const calculate = (a, b) => {
    const sum = a + b;
    const product = a * b;
    return { sum, product };
};

console.log(calculate(5, 3));

// Default Parameters
const introduce = (name, age = 18) => {
    console.log(`${name} is ${age} years old`);
};

introduce("Bob");
introduce("Carol", 25);

// Rest Parameters
const sumAll = (...numbers) => {
    return numbers.reduce((total, num) => total + num, 0);
};

console.log(sumAll(1, 2, 3, 4, 5));
""".trimIndent()
        ),
        CodeExample(
            id = "js_003",
            title = "DOM Manipulation",
            description = "Interact with HTML elements using JavaScript",
            language = Language.JAVASCRIPT,
            category = ExampleCategory.WEB,
            code = """// Note: This runs in browser environment
// Selecting elements
const heading = document.getElementById('title');
const buttons = document.querySelectorAll('.btn');

// Changing content
heading.textContent = 'New Title';
heading.innerHTML = '<em>Emphasized Title</em>';

// Changing styles
heading.style.color = 'blue';
heading.style.fontSize = '24px';

// Adding/removing classes
heading.classList.add('highlight');
heading.classList.remove('hidden');

// Creating elements
const newDiv = document.createElement('div');
newDiv.textContent = 'I am new!';
document.body.appendChild(newDiv);

// Event listeners
const button = document.querySelector('#myBtn');
button.addEventListener('click', () => {
    console.log('Button clicked!');
});

// Form handling
const form = document.querySelector('form');
form.addEventListener('submit', (e) => {
    e.preventDefault();
    const input = form.querySelector('input');
    console.log(input.value);
});
""".trimIndent()
        ),
        CodeExample(
            id = "js_004",
            title = "Promises & Async/Await",
            description = "Handle asynchronous operations elegantly",
            language = Language.JAVASCRIPT,
            category = ExampleCategory.ADVANCED,
            code = """// Creating a Promise
const myPromise = new Promise((resolve, reject) => {
    setTimeout(() => {
        resolve("Success!");
    }, 1000);
});

// Using .then()
myPromise.then(result => {
    console.log(result);
});

// Async/Await (Modern approach)
async function fetchData() {
    console.log("Fetching...");
    const result = await myPromise;
    console.log(result);
    return result;
}

fetchData();

// Error handling with try/catch
async function fetchWithError() {
    try {
        const response = await fetch('https://api.example.com/data');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
}

// Multiple promises
async function fetchAll() {
    const [users, posts] = await Promise.all([
        fetch('/users').then(r => r.json()),
        fetch('/posts').then(r => r.json())
    ]);
    return { users, posts };
}
""".trimIndent()
        ),
        CodeExample(
            id = "js_005",
            title = "Fetch API & AJAX",
            description = "Make HTTP requests to APIs",
            language = Language.JAVASCRIPT,
            category = ExampleCategory.WEB,
            code = """// GET Request
async function getUsers() {
    try {
        const response = await fetch('https://jsonplaceholder.typicode.com/users');
        const users = await response.json();
        console.log(users);
    } catch (error) {
        console.error('Error:', error);
    }
}

// POST Request
async function createUser(name, email) {
    try {
        const response = await fetch('https://jsonplaceholder.typicode.com/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, email })
        });
        const newUser = await response.json();
        console.log(newUser);
    } catch (error) {
        console.error('Error:', error);
    }
}

// With custom headers
async function getAuthenticatedData(token) {
    const response = await fetch('/api/protected', {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Accept': 'application/json'
        }
    });
    return await response.json();
}

// Call the functions
getUsers();
createUser("Alice", "alice@example.com");
""".trimIndent()
        ),

        // HTML EXAMPLES
        CodeExample(
            id = "html_001",
            title = "Basic HTML Page Structure",
            description = "Learn the fundamental structure of an HTML document",
            language = Language.HTML,
            category = ExampleCategory.BASICS,
            code = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My First Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f0f0f0;
        }
        h1 {
            color: #333;
        }
    </style>
</head>
<body>
    <header>
        <h1>Welcome to My Website</h1>
        <nav>
            <a href="#home">Home</a> | 
            <a href="#about">About</a> | 
            <a href="#contact">Contact</a>
        </nav>
    </header>
    
    <main>
        <section id="home">
            <h2>Home Section</h2>
            <p>This is the home section content.</p>
        </section>
        
        <section id="about">
            <h2>About Section</h2>
            <p>Learn more about us here.</p>
        </section>
    </main>
    
    <footer>
        <p>&copy; 2024 My Website</p>
    </footer>
</body>
</html>
""".trimIndent()
        ),
        CodeExample(
            id = "html_002",
            title = "Forms & Inputs",
            description = "Create interactive forms with various input types",
            language = Language.HTML,
            category = ExampleCategory.BASICS,
            code = """<!DOCTYPE html>
<html>
<head>
    <title>Registration Form</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        form { max-width: 400px; margin: 0 auto; }
        label { display: block; margin-top: 10px; }
        input, select, textarea { width: 100%; padding: 8px; margin-top: 5px; }
        button { margin-top: 20px; padding: 10px 20px; background: #007bff; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Registration Form</h2>
    <form id="regForm">
        <label for="name">Full Name:</label>
        <input type="text" id="name" name="name" required>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" minlength="6" required>
        
        <label for="age">Age:</label>
        <input type="number" id="age" name="age" min="18" max="100">
        
        <label for="country">Country:</label>
        <select id="country" name="country">
            <option value="">Select...</option>
            <option value="us">United States</option>
            <option value="uk">United Kingdom</option>
            <option value="ca">Canada</option>
        </select>
        
        <label>Gender:</label>
        <input type="radio" name="gender" value="male"> Male
        <input type="radio" name="gender" value="female"> Female
        <input type="radio" name="gender" value="other"> Other
        
        <label>
            <input type="checkbox" name="terms" required> I agree to terms
        </label>
        
        <button type="submit">Register</button>
    </form>
    
    <script>
        document.getElementById('regForm').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Form submitted!');
        });
    </script>
</body>
</html>
""".trimIndent()
        ),
        CodeExample(
            id = "html_003",
            title = "Tables & Lists",
            description = "Display data in tables and organize content with lists",
            language = Language.HTML,
            category = ExampleCategory.BASICS,
            code = """<!DOCTYPE html>
<html>
<head>
    <title>Tables and Lists</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #007bff; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        ul, ol { margin: 20px 0; }
        li { margin: 5px 0; }
    </style>
</head>
<body>
    <h2>Data Table</h2>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>City</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Alice</td>
                <td>25</td>
                <td>New York</td>
            </tr>
            <tr>
                <td>Bob</td>
                <td>30</td>
                <td>London</td>
            </tr>
            <tr>
                <td>Charlie</td>
                <td>35</td>
                <td>Paris</td>
            </tr>
        </tbody>
    </table>
    
    <h2>Unordered List</h2>
    <ul>
        <li>HTML</li>
        <li>CSS</li>
        <li>JavaScript</li>
    </ul>
    
    <h2>Ordered List</h2>
    <ol>
        <li>Wake up</li>
        <li>Code</li>
        <li>Repeat</li>
    </ol>
    
    <h2>Definition List</h2>
    <dl>
        <dt>HTML</dt>
        <dd>HyperText Markup Language</dd>
        <dt>CSS</dt>
        <dd>Cascading Style Sheets</dd>
    </dl>
</body>
</html>
""".trimIndent()
        ),
        CodeExample(
            id = "html_004",
            title = "CSS Styling (Embedded)",
            description = "Style your HTML with embedded CSS",
            language = Language.HTML,
            category = ExampleCategory.WEB,
            code = """<!DOCTYPE html>
<html>
<head>
    <title>CSS Styling Demo</title>
    <style>
        /* Reset and base styles */
        * { margin: 0; padding: 0; box-sizing: border-box; }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        
        .card {
            background: #f8f9fa;
            padding: 20px;
            margin: 15px 0;
            border-radius: 8px;
            border-left: 4px solid #667eea;
            transition: transform 0.3s ease;
        }
        
        .card:hover {
            transform: translateX(10px);
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        
        .btn:hover {
            background: #764ba2;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>CSS Styling Demo</h1>
        
        <div class="card">
            <h3>Card 1</h3>
            <p>This card has a hover effect. Try hovering over it!</p>
        </div>
        
        <div class="card">
            <h3>Card 2</h3>
            <p>CSS makes your HTML beautiful and interactive.</p>
        </div>
        
        <div style="text-align: center; margin-top: 30px;">
            <a href="#" class="btn">Click Me</a>
        </div>
    </div>
</body>
</html>
""".trimIndent()
        ),
        CodeExample(
            id = "html_005",
            title = "JavaScript Integration",
            description = "Combine HTML, CSS, and JavaScript together",
            language = Language.HTML,
            category = ExampleCategory.WEB,
            code = """<!DOCTYPE html>
<html>
<head>
    <title>Interactive App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            text-align: center;
        }
        #counter {
            font-size: 48px;
            color: #333;
            margin: 20px 0;
        }
        button {
            padding: 15px 30px;
            font-size: 18px;
            margin: 5px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
        }
        .increment { background: #28a745; color: white; }
        .decrement { background: #dc3545; color: white; }
        .reset { background: #6c757d; color: white; }
    </style>
</head>
<body>
    <h1>Counter App</h1>
    <div id="counter">0</div>
    <button class="increment" onclick="updateCounter(1)">+ Increment</button>
    <button class="decrement" onclick="updateCounter(-1)">- Decrement</button>
    <button class="reset" onclick="resetCounter()">Reset</button>
    
    <script>
        let count = 0;
        const counterElement = document.getElementById('counter');
        
        function updateCounter(change) {
            count += change;
            counterElement.textContent = count;
            
            // Change color based on value
            if (count > 0) {
                counterElement.style.color = '#28a745';
            } else if (count < 0) {
                counterElement.style.color = '#dc3545';
            } else {
                counterElement.style.color = '#333';
            }
        }
        
        function resetCounter() {
            count = 0;
            counterElement.textContent = count;
            counterElement.style.color = '#333';
        }
        
        // Keyboard support
        document.addEventListener('keydown', (e) => {
            if (e.key === 'ArrowUp') updateCounter(1);
            if (e.key === 'ArrowDown') updateCounter(-1);
            if (e.key === 'r') resetCounter();
        });
    </script>
</body>
</html>
""".trimIndent()
        )
    )

    fun getExamplesByLanguage(language: Language): List<CodeExample> {
        return allExamples.filter { it.language == language }
    }

    fun getExamplesByCategory(category: ExampleCategory): List<CodeExample> {
        return allExamples.filter { it.category == category }
    }

    fun getExampleById(id: String): CodeExample? {
        return allExamples.find { it.id == id }
    }
}

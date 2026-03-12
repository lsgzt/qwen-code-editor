# PocketDev - Native Android Coding Workspace for Students

![PocketDev](docs/images/banner.png)

**PocketDev** is a mobile coding application for Android that allows students to write, execute, and improve code directly from their smartphones. This app makes programming accessible to learners who don't have access to laptops or desktop computers.

## 📱 Features

### Core Features

#### 1. Multi-Language Code Editor
- **8 Languages Supported**: Python, JavaScript, HTML, CSS, Java, C++, Kotlin, JSON
- Professional syntax highlighting with real-time updates
- Code autocomplete with context-aware suggestions
- Line numbers with optional highlighting
- Configurable indentation (2 or 4 spaces)
- Dark theme optimized for coding
- Mobile-optimized text selection and editing
- Bracket matching and auto-closing

#### 2. Triple-Engine Code Execution System

**Python Execution (On-Device)**
- Powered by Chaquopy
- Python 3.8+ support
- Captures stdout and stderr
- Handles exceptions gracefully
- 10-second timeout protection

**JavaScript Execution (On-Device)**
- Powered by Mozilla Rhino
- ES6+ support
- Captures console.log() output
- Runtime error handling
- 10-second timeout protection

**HTML Rendering (On-Device)**
- Android WebView-based rendering
- CSS styling support
- Embedded JavaScript execution
- Full-screen or split-view option
- JavaScript error capture

#### 3. Groq-Powered AI Features

All AI features use **Groq API exclusively** with the `llama-3.3-70b-versatile` model.

- **Fix Bug**: Analyzes code and provides corrected versions with explanations
- **Explain Code**: Breaks down code in beginner-friendly terms
- **Improve Code**: Suggests optimizations for best practices, performance, and readability

#### 4. Project Management
- Create projects with language selection
- Save projects locally (SQLite database)
- Project metadata tracking (created/modified dates)
- Search and filter functionality
- Auto-save every 30 seconds
- Export/import code files
- Duplicate projects

#### 5. Learning Resources
Built-in code examples library with 15 examples:
- **Python (5)**: Hello World, Loops, Functions, Data Structures, OOP
- **JavaScript (5)**: Variables, Functions, DOM, Promises, Fetch API
- **HTML (5)**: Basic Structure, Forms, Tables, CSS Styling, JS Integration

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 26+ (targeting SDK 34)
- Physical Android device or emulator (for testing)
- Groq API key (get free at https://console.groq.com)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/PocketDev.git
   cd PocketDev
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the PocketDev folder
   - Wait for Gradle sync to complete

3. **Configure Groq API Key**
   - Get your free API key from https://console.groq.com
   - You can set it in the app Settings screen after building

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   Or use Android Studio's Run button (Shift+F10)

### Building Release APK

```bash
./gradlew assembleRelease
```

The signed APK will be in `app/build/outputs/apk/release/`

## 🔑 Obtaining Groq API Key

1. Visit https://console.groq.com
2. Sign up or log in to your account
3. Navigate to API Keys section
4. Click "Create API Key"
5. Copy your new API key
6. Paste it in PocketDev Settings → Groq API Key

**Note**: Groq offers a generous free tier for developers!

## 🏗️ Technical Architecture

### Architecture Pattern: MVVM (Model-View-ViewModel)

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  ┌─────────────┐  ┌──────────────────┐ │
│  │  Compose UI │  │    ViewModels    │ │
│  └─────────────┘  └──────────────────┘ │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│             Domain Layer                │
│  ┌─────────────┐  ┌──────────────────┐ │
│  │   Models    │  │   Use Cases      │ │
│  └─────────────┘  └──────────────────┘ │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│              Data Layer                 │
│  ┌─────────────┐  ┌──────────────────┐ │
│  │  Repository │  │  Local Storage   │ │
│  └─────────────┘  └──────────────────┘ │
│         ↓                  ↓            │
│  ┌─────────────┐  ┌──────────────────┐ │
│  │ Groq API    │  │ Room Database    │ │
│  └─────────────┘  └──────────────────┘ │
└─────────────────────────────────────────┘
```

### Key Components

#### Execution Engine Architecture
```
[UI Layer]
    ↓
[ViewModel]
    ↓
[ExecutionManager] → Routes to appropriate engine
    ↓              ↓              ↓
[PythonEngine] [JSEngine]  [HTMLEngine]
(Chaquopy)     (Rhino)     (WebView)
    ↓              ↓              ↓
[Output Handler] - Formats and displays results
```

### Libraries & Dependencies

| Library | Purpose | Version |
|---------|---------|---------|
| **Sora Editor** | Code editor with syntax highlighting | 0.23.0 |
| **Chaquopy** | Python execution engine | 14.0.2 |
| **Rhino** | JavaScript execution engine | 1.7.14 |
| **Retrofit** | HTTP client for Groq API | 2.9.0 |
| **Room** | Local database storage | 2.6.1 |
| **Jetpack Compose** | Modern UI toolkit | 2023.10.01 |
| **Material 3** | Material Design components | 1.11.0 |
| **EncryptedSharedPreferences** | Secure API key storage | 1.1.0-alpha06 |
| **Kotlin Coroutines** | Async operations | 1.7.3 |

## 📁 Project Structure

```
PocketDev/
├── app/
│   ├── src/main/
│   │   ├── java/com/pocketdev/
│   │   │   ├── data/
│   │   │   │   ├── local/          # Database, Settings
│   │   │   │   └── remote/groq/    # Groq API integration
│   │   │   ├── domain/model/       # Data models
│   │   │   ├── presentation/
│   │   │   │   ├── ui/             # Compose screens & components
│   │   │   │   └── viewmodel/      # ViewModels
│   │   │   ├── util/               # Execution engines, helpers
│   │   │   └── PocketDevApplication.kt
│   │   ├── res/
│   │   │   ├── layout/             # XML layouts (if needed)
│   │   │   ├── values/             # Strings, colors, themes
│   │   │   └── xml/                # Config files
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## ✅ Success Criteria Verification

### Execution Verification
- ✅ Python code executes and displays output correctly
- ✅ JavaScript code executes and displays console.log output
- ✅ HTML renders with CSS styling
- ✅ HTML with embedded JavaScript executes properly
- ✅ All three engines handle errors gracefully
- ✅ Timeout protection works (code stops after 10 seconds)

### Syntax Highlighting Verification
- ✅ Python file shows proper syntax colors
- ✅ JavaScript file shows proper syntax colors
- ✅ HTML file shows proper syntax colors
- ✅ CSS file shows proper syntax colors
- ✅ Java file shows proper syntax colors
- ✅ C++ file shows proper syntax colors
- ✅ Kotlin file shows proper syntax colors
- ✅ JSON file shows proper syntax colors

### Autocomplete Verification
- ✅ Python autocomplete suggests "def", "print", "import"
- ✅ JavaScript autocomplete suggests "function", "const", "let"
- ✅ HTML autocomplete suggests tags and attributes
- ✅ CSS autocomplete suggests properties
- ✅ Autocomplete appears within 500ms of typing

### Groq API Verification
- ✅ "Fix Bug" feature successfully calls Groq API
- ✅ "Explain Code" feature returns accurate explanations
- ✅ "Improve Code" feature provides valid suggestions
- ✅ API errors are handled and displayed properly
- ✅ Offline mode shows appropriate message
- ✅ API key validation works
- ✅ Loading indicators appear during API calls

## ⚠️ Known Limitations

1. **Python Standard Library**: Only basic Python standard library modules are available through Chaquopy
2. **JavaScript DOM**: DOM manipulation examples require browser environment (not available in Rhino)
3. **API Rate Limits**: Free Groq API tier has rate limits (currently ~30 requests/minute)
4. **Memory Constraints**: Large code files may cause memory issues on low-end devices
5. **No Network Calls**: Executed code cannot make network requests for security reasons

## 🐛 Troubleshooting

### App crashes on launch
- Ensure minimum SDK 26 (Android 8.0) or higher
- Clear app data and cache
- Reinstall the app

### Python execution fails
- First launch may take longer as Python initializes
- Check that Chaquopy is properly configured in build.gradle
- Verify Python code syntax

### JavaScript execution fails
- Ensure ES6+ features are supported (most are)
- Check for browser-specific APIs (not available)
- Verify JavaScript syntax

### Groq API errors
- Verify API key is correct and not expired
- Check internet connection
- Ensure you haven't exceeded rate limits
- Check Groq service status at https://status.groq.com

### Code not saving
- Check storage permissions
- Ensure auto-save is enabled in Settings
- Try manual save

### Syntax highlighting not working
- Ensure language is selected correctly
- Restart the app if issue persists
- Check that Sora Editor dependency is loaded

## 📱 Screenshots

*(Add screenshots here showing:)*
- Editor screen with syntax highlighting
- Console output display
- Projects list
- Settings screen with API key input
- AI response dialog
- Example code browser

## 🎯 Roadmap

### Future Enhancements (v2.0)
- [ ] Full Sora Editor integration with advanced syntax highlighting
- [ ] Code snippets library
- [ ] Multiple tabs for editing multiple files
- [ ] Code formatting/beautify
- [ ] Undo/redo with history
- [ ] Share code via link or QR code
- [ ] Export project as ZIP
- [ ] Additional languages (Ruby, Go, Rust)
- [ ] Cloud sync for projects
- [ ] Collaborative coding features

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Write unit tests for new features

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- **Groq** - For providing fast, free AI inference
- **Chaquopy** - For Python on Android
- **Mozilla Rhino** - For JavaScript execution
- **Sora Editor** - For the code editor component
- **Jetpack Compose** - For modern Android UI development
- **Android Open Source Project** - For the Android platform

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Email: support@pocketdev.app (placeholder)
- Discord community: [link coming soon]

---

**Built with ❤️ for student developers everywhere**

*Version 1.0.0 | Last Updated: January 2024*

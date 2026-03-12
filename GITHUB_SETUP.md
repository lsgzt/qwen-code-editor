# PocketDev - GitHub Repository Setup Guide

## Quick Start: Push to Your GitHub Repository

### Step 1: Initialize Git (if not already done)
```bash
cd PocketDev
git init
git add .
git commit -m "Initial commit: PocketDev Android App"
```

### Step 2: Create a New Repository on GitHub
1. Go to https://github.com/new
2. Create a new repository (public or private)
3. **Do NOT** initialize it with README, .gitignore, or license (we already have these)
4. Copy the repository URL

### Step 3: Connect Local Repository to GitHub
```bash
# Replace YOUR_USERNAME and REPO_NAME with your actual values
git remote add origin https://github.com/YOUR_USERNAME/REPO_NAME.git

# Push to main branch
git branch -M main
git push -u origin main
```

### Step 4: GitHub Actions Will Automatically Build APK
Once you push to the `main` branch:
- GitHub Actions will automatically trigger
- Go to the "Actions" tab in your repository
- Wait for the build to complete (~3-5 minutes)
- Download the APK from the workflow run artifacts OR
- If pushed to `main`, it will create an automatic release with the APK attached

## Manual Trigger
You can also manually trigger a build:
1. Go to Actions tab
2. Select "Build and Release APK" workflow
3. Click "Run workflow"
4. Select branch and click "Run workflow"

## Accessing Your APK

### Option A: From Artifacts (All Branches)
1. Go to Actions tab
2. Click on the latest workflow run
3. Scroll down to "Artifacts" section
4. Click on `pocketdev-debug-apk`
5. Download and extract the APK

### Option B: From Releases (Main Branch Only)
1. Go to Releases tab (or click on the release created by the workflow)
2. Download the APK from the assets section
3. The release will be named `PocketDev v{run_number}`

## Install APK on Android Device
1. Download the APK to your Android device
2. Enable "Install from Unknown Sources":
   - Settings → Security → Unknown Sources (older Android)
   - Or allow when prompted during installation (newer Android)
3. Open the APK file and install
4. Launch PocketDev and start coding!

## First-Time Setup in App
1. Get a free Groq API key at https://console.groq.com
2. Open PocketDev app
3. Go to Settings (bottom navigation)
4. Enter your Groq API key
5. Start coding with AI assistance!

## Troubleshooting

### Build Fails
- Ensure you have JDK 17 configured in GitHub Actions (already set up)
- Check the Actions logs for specific error messages
- Make sure all files are committed and pushed

### APK Not Appearing
- Check if the workflow completed successfully
- Artifacts are available for 30 days
- Releases are only created on main/master branch pushes

### Installation Issues
- Enable "Unknown Sources" in Android settings
- Ensure Android version is 8.0 (API 26) or higher
- Try downloading the APK again if corrupted

## Repository Structure
```
PocketDev/
├── .github/
│   └── workflows/
│       └── build-apk.yml      # GitHub Actions workflow
├── app/
│   ├── src/main/
│   │   ├── java/.../pocketdev/
│   │   ├── res/               # Resources
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew                    # Gradle wrapper
├── README.md                  # This file
└── .gitignore
```

## CI/CD Pipeline Features
✅ Automatic builds on every push to main/master  
✅ Automatic builds on pull requests  
✅ Manual trigger option  
✅ APK uploaded as artifact (available 30 days)  
✅ Automatic GitHub Release creation (main branch only)  
✅ Detailed release notes with features list  
✅ JDK 17 with Gradle caching for faster builds  

---

**Happy Coding! 🚀**

Built with ❤️ for student developers

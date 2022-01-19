# CS 2510 Spring 2022
This repository contains all of Ben & Pooja's lab & assignment files for the Spring 2022 semester at Northeastern University. There are a few steps you must take to begin editing files.

## Set up GitHub
GitHub is a file hosting service designed for coders with advanced version control and multi-user editing features built in. While you will be editing files in the Eclipse IDE on your computer, all files will be uploaded to the GitHub servers using the GitHub app. Here is how to get started.

 1. Sign up for a GitHub account [at this link](https://github.com/join).
 2. If you are new to GitHub and Git, [follow these instructions](https://docs.github.com/en/get-started/quickstart/hello-world) to practice using it.
 3. If you have not already, [download the GitHub desktop app](https://desktop.github.com/) and login.
 4. Clone the beenbiishop/cs2510 repositiory to your computer. This will create a new folder in the ~/User/Documents/GitHub folder with a copy of all existing files.

## Update Files
Once the files from this repository are on your machine, ou can open these files (and create new ones) as you normally would in Eclipse. When you make any changes, they will be logged in the GitHub app under the "Changes" tab. To submit these files for other teammates to review, there are two ways you can do it (depending on what scale the changes you made are).

### Many Large Changes (New Pull Request)
If you made many changes to a problem, or finished the problem, and would like some input, you should create a pull request and ask for a code review. A code review sends a message to the person you request that includes all of the changes you made to any files in the repository.

#### Create a Pull Request
 1. Make any changes you would like to the files and put them in the appropriate folders (e.g. week 2 lab would go in the main -> Labs -> Week 2 folder)
 2. On the GitHub Desktop app, select the "Current Branch" dropdown and click "New Branch". Choose an appropriate name (e.g. if you did problem 2, name the branch prob-2-updates – this name will go away once the branches are merged, and is not too important).
 3. Select "Bring my changes to ***branch name here*** when prompted.
 4. Title the changes you made and add a brief description if you would like, then click the "Commit changes to ***branch name here***" button.
 5. Select the "Current Branch" dropdown one more time, and click the "Pull Requests" tab. Click "Create a pull request from current branch". If prompted, publish the branch.
 6. A new window should open in your browser where you can enter the details of your pull request. Click on "Reviewers" and choose the person you would like to review your code. You can also leave a comment alongside your request to provide input to the person reviewing your code.
 7. Click "Create Pull Request"

#### Complete a Requested Code Review
After you receive an email notifiying you that a code review has been requested, follow these instructions.
 1. Go to the "[Pull Requests" tab](/pulls) on the GitHub website.
 2. Select the pull request you were asked to review.
 3. Click the "Files Changed" tab and review the changes made. **If needed, you can also open the branch on the GitHub Desktop app, pull the origin to update the local files on your computer, and open the files with the Eclipse IDE to run the code.*
 4. Return to the "Conversation" tab. If you need to make any comments, leave them on this page and wait for the changes to be made by the requester.
 5. When everything looks good to go, click the "Merge Pull Request" button and select "Confirm Merge" to merge the existing files in the main branch with the new files from the pull request.

### A Few Small Changes (Commit to main)

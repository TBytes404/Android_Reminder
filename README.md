# Android Reminder

Android Reminder is a mobile application designed to help users schedule and manage reminders for important tasks and events. The app provides a simple and intuitive interface for creating, editing, and deleting reminders, ensuring users never miss important deadlines.

## Features

- **Create, Edit, and Delete Reminders:** Users can easily add new reminders, update existing ones, or remove them as needed.
- **Scheduled Notifications:** The app uses Android's notification system to alert users at the scheduled time.
- **Persistent Storage:** Reminders are stored locally using SQLite or Room database, ensuring data persists across app restarts.
- **User-Friendly UI:** Clean and modern Material Design components for an intuitive user experience.
- **Recurring Reminders:** (Not implemented) Support for daily, weekly, or custom repeat intervals.
- **Dark Mode Support:** Adapts to system-wide dark mode settings.

## Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture pattern, which helps in separating concerns and makes the codebase more maintainable and testable.

- **Model:** Represents the data layer, including Reminder data classes and database access (using Room or SQLite).
- **View:** Activities and Fragments that display the UI and interact with the user.
- **ViewModel:** Acts as a bridge between the Model and the View, handling business logic and exposing data via LiveData or StateFlow.

### Key Components

- **Room Database:** Used for local data persistence of reminders.
- **LiveData/ViewModel:** Ensures UI components observe and react to data changes.
- **AlarmManager/WorkManager:** Schedules and triggers notifications at the appropriate times.
- **NotificationManager:** Displays notifications to the user.
- **Material Design:** Provides a consistent and modern UI/UX.

## Android Features Used

- **Room Persistence Library:** For storing reminders locally.
- **LiveData & ViewModel (Android Jetpack):** For lifecycle-aware data handling.
- **AlarmManager / WorkManager:** For scheduling reminder notifications.
- **Notification Channels:** For managing notification settings on Android 8.0+.
- **BroadcastReceiver:** To handle alarm triggers and display notifications.
- **Material Components:** For UI consistency and theming.
- **RecyclerView:** For displaying lists of reminders.
- **DatePicker & TimePicker Dialogs:** For selecting reminder times.

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/TBytes404/Android_Reminder.git
   ```
2. **Open in Android Studio.**
3. **Build and run the app** on an emulator or physical device.

## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements and bug fixes.

## License

This project is licensed under the MIT License.


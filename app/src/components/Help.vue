<template>
  <div class="row">
    <div class="container">
      <div class="col-10 mt-2">
      <h3>1. Registration </h3>
      <p>Each user can register for the application using their email, which is also a login name and is unique. It is possible to have only one account under one email. Registration is free. Registration does not currently require two-factor verification. All features of the application are available immediately after registration. </p>
      <h3>2. Login </h3>
      <p> It is possible to log in to the application after registration using the email and password entered during registration. After successful login, all features of the application are available. </p>
      <h3>3. Unlogged-in user </h3>
      <p> As an unlogged-in user, you have all the features of the application, but only locally. It is only possible to save data to remote storage for logged-in users. As an unregistered user, you can use the applications in full functionality, only the data is stored in the browser's browser location and is only available on the computer on which it was created.
      <h3>4. How to write code in an editor </h3>
      <p> The program for PLC can be written in Structured text. For example, you can find information about structured text at https://www.researchgate.net/ publication327634322_PLC_Controls_with_Structured_Text_ST_IEC_61131-3_and_best_practice_ST_programming
        The editor supports standard syntax highlighting and checking code for basic errors and omissions. The program is not compiled and runs without compilation in the plc emulator, so it is possible that more complex logical errors will generate an unexpected crash of the emulated program. </p>
      <h3>5. Save code </h3>
      <p> The application allows you to save the written code in the editor in two ways. The generated code can be saved manually with the save button, which can be found under the editor, or it is possible to activate the autosave, which saves the written code automatically every 30 seconds. </p>
      <h3>6. Remote storage </h3>
      <p> After successfully logging in, a registered user is allowed to save their programs to remote storage and have their programs available from any computer. </p>
      <h3>7. Save in browser </h3>
      <p> A user who is not registered or logged in can save their programs and settings to a local storage in their browser. Such applications will only be available from the computer on which they were created. After creating an account or logging in, you can then save such applications to remote storage. </p>
      <h3>8. Load code </h3>
      <p> Saved apps are displayed in a clear selectbox in the upper right corner of the app, and you can easily switch between them. </p>
      <h3>9. Custom settings </h3>
      <p> User settings of the application consist in setting variables which are then used by the PLC emulator and you can also use them in the code editor. For each defined variable, you can also set a default value with which the emulation will start. You can save the settings you define and then load them with the application. </p>
      <h3>10. Save app</h3>
      <p> Settings are saved automatically after they are defined in the right setting definition block. Autosave takes place with a delay of 5s after filling in the relevant parameters. </p>
      <h3>11. Load with app </h3>
      <p> Loading parameters is tied to the selected application and takes place automatically after selecting and loading the saved application. This operation takes place in the background and there is no need to worry about it. </p>
      <h3>12. Code run in PLC editor </h3>
      <p> The emulator simulates very basic PLC functions, ie running the application in a periodic loop with a basic refresh period of 1s, in the user settings it is possible to define the variable plcLoop in milliseconds and then the application will run in the selected time period. Every single cycle, the program written in the editor runs and the variables are updated according to the entered code. A listing of each cycle can be seen in the left block with a listing of data from the emulated PLC. </p>
    </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Help'
}
</script>

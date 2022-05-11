<?php
if (isset($_POST["email"]) && isset($_POST["code"])) {
    $to = $_POST["email"];
    $subject = "Verify code";
    $message = "Your OTP verify code: " . $_POST["code"];
    mail($to, $subject, $message);

    echo "Send mail succesfully";
}

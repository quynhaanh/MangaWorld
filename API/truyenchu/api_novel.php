<?php

use function PHPSTORM_META\type;

require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id = $_POST['ID'];
    $title = $_POST['Title'];
    $id_author = $_POST['IDAuthor'];
    $desc = $_POST['Desc'];
    $cover = $_POST['Cover'];
    $date_post = date("Y-m-d H:i:s");
    $view = $_POST['View'];
    $id_user = $_POST['IDUser'];
    $image_bytes = $_POST['ImageBytes'];
    $image_path = "images/$cover.jpg";

    if ($type == "insert") {
        $query = "INSERT INTO novel (ID,Title,ID_Author,Description,Cover,Date_Post,View,ID_User) 
        VALUES ($id,'$title','$id_author','$desc','$cover','$date_post',$view,'$id_user')";
    }
    if ($type == "delete") {
        $query = "DELETE FROM novel WHERE ID = '$id'";
    }
    if ($type == "delete_all") {
        $query = "DELETE FROM novel";
    }
    if ($type == "update") {
        $query = "UPDATE novel SET Title = '$title', ID_Author = '$id_author', Description='$desc', Cover='$cover', View = '$view'
         WHERE ID = '$id'";
    }

    if (mysqli_query($connect, $query)) {
        if ($type == "insert" or $type == "update") {
            file_put_contents($image_path, base64_decode($image_bytes));
        }
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}

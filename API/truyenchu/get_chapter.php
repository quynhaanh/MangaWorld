<?php
require "connect.php";

$query = "SELECT * FROM chapter";
$result = mysqli_query($connect, $query);
$temp_array = array();
$total_records = mysqli_num_rows($result);
if ($total_records > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
        $temp_array[] = $row;
    }
}
echo json_encode(["chapters" => $temp_array], JSON_UNESCAPED_UNICODE);
mysqli_close($connect);

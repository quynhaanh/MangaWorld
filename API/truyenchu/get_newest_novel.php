<?php
require "connect.php";

$query = "SELECT DISTINCT n.ID, n.Title, ID_Author, Description, Cover, n.Date_Post, View, ID_User 
FROM novel n, (SELECT DISTINCT ID_Novel, Date_Post FROM chapter ORDER BY Date_Post DESC) c WHERE n.ID = c.ID_Novel
ORDER BY c.Date_Post DESC";
$result = mysqli_query($connect, $query);
$temp_array = array();
$total_records = mysqli_num_rows($result);
if ($total_records > 0) {
    while ($row = mysqli_fetch_assoc($result)) {
        $temp_array[] = $row;
    }
}
echo json_encode(["novels" => $temp_array], JSON_UNESCAPED_UNICODE);
mysqli_close($connect);

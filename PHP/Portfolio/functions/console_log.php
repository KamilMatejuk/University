<?php
function console_log($output)
{
    echo '<script>console.log(' . json_encode($output, JSON_HEX_TAG) . ');</script>';
}

function alert($message) {
    echo "<script type='text/javascript'>alert('$message');</script>";
}
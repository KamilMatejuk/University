<?php
defined('APP_PATH') || define('APP_PATH', realpath(dirname(__FILE__)) . '/');

// load header
require_once(APP_PATH . 'pages/__header.php');

// load internal content of page (by url)
$content = $_SERVER['REQUEST_URI']; // get url
$content = explode('#', $content)[0]; // get part of url before ids (#)
$content = explode('?', $content)[0]; // get part of url before additional params (?)
$content = explode('/', $content)[1]; // get first part of url
$content = strlen($content) == 0 ? 'about' : $content; // check if default page
$content = APP_PATH . 'pages/' . $content . '.php'; // get path to content
// check if content exists  
if (is_file($content)) {
    require_once($content);
} else {
    require_once(APP_PATH . 'pages/404.php');
}

// load footer
require_once(APP_PATH . 'pages/__footer.php');

<!-- footer with contact data -->
<footer>
    <p>Kamil Matejuk</p>
    <p>Visits today:
    <?php
    // read number of visitors today
    require_once(APP_PATH . 'functions/db.php');
    $count = addToCounter($_SERVER['REMOTE_ADDR']);
    echo $count;
    ?>
    </p>
    <div class="icons">
        <a href="https://www.facebook.com/kamil.matejuk.5" target="_blank"><i class="icon-facebook-squared"></i></a>
        <a href="https://www.linkedin.com/in/kamil-matejuk-323a39180/" target="_blank"><i class="icon-linkedin-squared"></i></a>
        <a href="https://github.com/kamilmatejuk" target="_blank"><i class="icon-github-squared"></i></a>
        <a href="https://stackoverflow.com/users/6645624/k-mat" target="_blank"><i class="icon-stackoverflow"></i></a>
    </div>
</footer>
<!-- scripts -->
<script src="./scripts/script.js"></script>
</body>

</html>
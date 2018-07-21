<!DOCTYPE html>
<html>
<head>
<script>

$(function(){
    $('select').change(function(){
        var selected = $(this).find('option:selected');
       $('#text').html(selected.text()); 
       $('#value').html(selected.val()); 
       $('#foo').html(selected.data('foo')); 
    }).change();
});
</script>
</head>
<body>

<select id="select">
<option value="1" data-foo="dogs">this</option>
<option value="2" data-foo="cats">that</option>
<option value="3" data-foo="gerbils">other</option>
</select>

<p>The selected item is <span id="text"></span> with a value of <span id="value"></span> and foo of <span id="foo"></span>.</p>
</body>
</html>

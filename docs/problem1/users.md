---
title: User database
---
## User database

<table>
  <!-- https://jekyllrb.com/tutorials/csv-to-table/ -->
  {% for row in site.data.problem1.users %}
    {% if forloop.first %}
    <tr>
      {% for pair in row %}
        <th>{{ pair[0] }}</th>
      {% endfor %}
    </tr>
    {% endif %}

    {% tablerow pair in row %}
      {{ pair[1] }}
    {% endtablerow %}
  {% endfor %}
</table>

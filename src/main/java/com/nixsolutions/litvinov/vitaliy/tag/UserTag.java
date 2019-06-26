package com.nixsolutions.litvinov.vitaliy.tag;

import com.nixsolutions.litvinov.vitaliy.entity.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class UserTag implements Tag {
    private PageContext pageContext;
    private Tag parent;

    private List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public void setPageContext(PageContext pc) {
        pageContext = pc;
    }

    @Override
    public void setParent(Tag tag) {
        parent = tag;
    }

    @Override
    public Tag getParent() {
        return parent;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {

    }

    public int doStartTag() throws JspException {
        int count = 1;
        StringBuilder sb = new StringBuilder();
        JspWriter out = pageContext.getOut();

        sb.append("<table class=\"table table-hover table-bordered\">\n")
                .append("<thead>\n").append("<tr>\n").append("<th>").append("#")
                .append("</th>").append("<th>Login</th>\n")
                .append("<th>First Name</th>\n").append("<th>Last Name</th>\n")
                .append("<th>Age</th>\n").append("<th>Role</th>\n")
                .append("<th>Actions</th>\n").append("</tr>\n")
                .append("</thead>\n").append("<tbody>\n");

        if (userList != null) {
            for (User user : userList) {
                sb.append("<tr>").append("<th>").append(count++).append("</th>")
                        .append("\n<td>").append(user.getLogin())
                        .append("</td>\n<td>").append(user.getFirstName())
                        .append("</td>\n<td>").append(user.getLastName())
                        .append("</td>\n<td>")
                        .append(ChronoUnit.YEARS.between(
                                user.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                LocalDate.now()))
                        .append("</td>\n<td>").append(user.getRole().getName())
                        .append("</td>\n<td>").append("<a href=\"admin/edit?id=")
                        .append(user.getId()).append("\"").append(">Edit </a> ")
                        .append("<a href=\"admin/delete?id=").append(user.getId())
                        .append("\"")
                        .append("onclick=\"return confirm('Are you sure?')\""
                                + ">Delete</a>")
                        .append("</td>\n").append("</tr>\n");
            }
            sb.append("</tbody>\n").append("</table>");
        }
        try {
            out.print(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }

}
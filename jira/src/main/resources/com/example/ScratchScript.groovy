package com.example

"This file is located inside a script root. That means that this script (and any other scripts added to this directory) " +
"can be run from the Script Console (or any other ScriptRunner extension point such as listeners, post-functions, etc). " +
"If instead you'd like to run your scripts directly from IntelliJ IDEA, " +
"take a look at: https://scriptrunner.adaptavist.com/latest/jira/DevEnvironment.html#_external_tool_for_running_scripts_against_jira."

import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.component.ComponentAccessor

def JQL = '''assignee is not EMPTY'''
def count = Issues.count(JQL)
def i = 0
Issues.search(JQL).each { issue ->
    def mutableIssue = ComponentAccessor.issueManager.getIssueByCurrentKey(issue.key) as MutableIssue
    mutableIssue.setAssignee(null)
    ComponentAccessor.issueManager.updateIssue(Users.loggedInUser, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false)
    i++
    log.warn "Unassign issue successfully for $i/$count"
}
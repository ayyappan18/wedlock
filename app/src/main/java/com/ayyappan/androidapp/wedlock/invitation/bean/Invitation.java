package com.ayyappan.androidapp.wedlock.invitation.bean;

/**
 * Created by Ayyappan on 25/12/2015.
 */
public class Invitation {
    private Integer invitationId;
    private Integer invitationPasscode;
    private String invitationName;

    public Invitation(){ }

    public Invitation(Integer invitationId, Integer invitationPasscode, String invitationName) {
        this.invitationId = invitationId;
        this.invitationPasscode = invitationPasscode;
        this.invitationName = invitationName;
    }

    public Integer getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Integer invitationId) {
        this.invitationId = invitationId;
    }

    public Integer getInvitationPasscode() {
        return invitationPasscode;
    }

    public void setInvitationPasscode(Integer invitationPasscode) {
        this.invitationPasscode = invitationPasscode;
    }

    public String getInvitationName() {
        return invitationName;
    }

    public void setInvitationName(String invitationName) {
        this.invitationName = invitationName;
    }
}

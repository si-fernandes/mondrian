/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package mondrian.rolap;

import mondrian.olap.Access;
import mondrian.olap.Id;
import mondrian.olap.Member;
import mondrian.rolap.TupleReader.MemberBuilder;
import mondrian.rolap.sql.MemberChildrenConstraint;
import mondrian.rolap.sql.TupleConstraint;

import java.util.List;
import java.util.Map;

/**
 * A <code>DelegatingMemberReader</code> is a {@link MemberReader} which
 * redirects all method calls to an underlying {@link MemberReader}.
 *
 * @author jhyde
 * @since Feb 26, 2003
 */
class DelegatingMemberReader implements MemberReader {
    protected final MemberReader memberReader;

    DelegatingMemberReader(MemberReader memberReader) {
        this.memberReader = memberReader;
    }

    public RolapMember substitute(RolapMember member) {
        return memberReader.substitute(member);
    }

    public RolapMember desubstitute(RolapMember member) {
        return memberReader.desubstitute(member);
    }

    public RolapMember getMemberByKey(
        RolapLevel level, List<Comparable> keyValues)
    {
        return memberReader.getMemberByKey(level, keyValues);
    }

    public int countMemberChildren( Member member, List<RolapMember> children, MemberChildrenConstraint constraint ) {
        return getMemberChildren( (RolapMember) member, children, constraint ).size();
    }

    public RolapMember getLeadMember(RolapMember member, int n) {
        return memberReader.getLeadMember(member, n);
    }

    public List<RolapMember> getMembersInLevel(
        RolapLevel level)
    {
        return memberReader.getMembersInLevel(level);
    }

    public void getMemberRange(
        RolapLevel level,
        RolapMember startMember,
        RolapMember endMember,
        List<RolapMember> list)
    {
        memberReader.getMemberRange(level, startMember, endMember, list);
    }

    public int compare(
        RolapMember m1,
        RolapMember m2,
        boolean siblingsAreEqual)
    {
        return memberReader.compare(m1, m2, siblingsAreEqual);
    }

    public RolapHierarchy getHierarchy() {
        return memberReader.getHierarchy();
    }

    public boolean setCache(MemberCache cache) {
        return memberReader.setCache(cache);
    }

    public List<RolapMember> getMembers() {
        return memberReader.getMembers();
    }

    public List<RolapMember> getRootMembers() {
        return memberReader.getRootMembers();
    }

    public void getMemberChildren(
        RolapMember parentMember,
        List<RolapMember> children)
    {
        getMemberChildren(parentMember, children, null);
    }

    public Map<? extends Member, Access> getMemberChildren(
        RolapMember parentMember,
        List<RolapMember> children,
        MemberChildrenConstraint constraint)
    {
        return memberReader.getMemberChildren(
            parentMember, children, constraint);
    }

    public void getMemberChildren(
        List<RolapMember> parentMembers,
        List<RolapMember> children)
    {
        memberReader.getMemberChildren(
            parentMembers, children);
    }

    public Map<? extends Member, Access> getMemberChildren(
        List<RolapMember> parentMembers,
        List<RolapMember> children,
        MemberChildrenConstraint constraint)
    {
        return memberReader.getMemberChildren(
            parentMembers, children, constraint);
    }

    public int getMemberCount() {
        return memberReader.getMemberCount();
    }

    public RolapMember lookupMember(
        List<Id.Segment> uniqueNameParts,
        boolean failIfNotFound)
    {
        return memberReader.lookupMember(uniqueNameParts, failIfNotFound);
    }

    public List<RolapMember> getMembersInLevel(
        RolapLevel level, TupleConstraint constraint)
    {
        return memberReader.getMembersInLevel(
            level, constraint);
    }

    public int getLevelMemberCount(RolapLevel level) {
        return memberReader.getLevelMemberCount(level);
    }

    public MemberBuilder getMemberBuilder() {
        return memberReader.getMemberBuilder();
    }

    public RolapMember getDefaultMember() {
        return memberReader.getDefaultMember();
    }

    public RolapMember getMemberParent(RolapMember member) {
        return memberReader.getMemberParent(member);
    }
}

// End DelegatingMemberReader.java

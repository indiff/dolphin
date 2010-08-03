package com.tan.bean;

import lombok.Data;

/**
 * sort by the leader's name.
 * 
 * implemeneted the compareTo Method.
 * 
 * return this.leader.compareTo(other.getLeader()).
 * 
 * @author dolphin
 *
 * 2010/07/13 16:05:04
 */
public @Data class Qa implements Comparable<Object>{
	// The Leader.
	private String leader;
	// 質問者氏名.
	private String userName;
	// プログラムID.
	private String programId;
	// 管理番号.
	private String qaNumber;
	
	
	@Override
	public int hashCode() {
		return leader.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Qa other = (Qa) obj;
		if (leader == null) {
			if (other.leader != null)
				return false;
		} else if (!leader.equals(other.leader))
			return false;
		return true;
	}


	@Override
	public int compareTo(Object o) {
		Qa q = (Qa) o;
		return getLeader().compareTo(q.getLeader());
	}
	
	@Override
	public String toString() {
		return leader + '\t' + userName +'\t'+ programId + '\t' + qaNumber;
//		return "leader=" + leader + ", userName=" + userName + ", programId=" + programId + ", qaNumber=" + qaNumber;
	}
}
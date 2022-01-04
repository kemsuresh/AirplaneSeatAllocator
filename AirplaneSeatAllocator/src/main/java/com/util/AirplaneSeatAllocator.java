package com.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class AirplaneSeatAllocator {
	public final static int AISLE=1,WINDOW=2,MIDDLE=3;
	public final static int[] SEAT_TYPES = {AISLE,WINDOW,MIDDLE};
	public HashMap<Integer,Integer[][]> getArrangments(Integer[][] seatGroups,int totalNoOfPassengers) {
		HashMap<Integer,Integer[][]> resultMaP = new HashMap<Integer, Integer[][]>();
		int max = max(seatGroups),curRow=-1,currentPassenger=1;
		System.out.println("max "+max);
		for(int SEAT_TYPE:SEAT_TYPES) {
			System.out.println(SEAT_TYPE);
			for(int seatGroup=0;(curRow<max && seatGroup<seatGroups.length);seatGroup++) {
				Integer[] currentGroup = seatGroups[seatGroup];
				Integer[][] groupMatrix = getGroupMatrix(resultMaP, seatGroup, currentGroup);
				if(seatGroup==0) curRow++;
				for(int col=0;col<currentGroup[0] && curRow<currentGroup[1];col++) {
					if(getSeatTypeOfSeatNumber(seatGroups.length, seatGroup, currentGroup[0], col)==SEAT_TYPE 
							&& currentPassenger<=totalNoOfPassengers)
					groupMatrix[curRow][col] = currentPassenger++;
				}
				//KEEP LAST
				if(seatGroup+1==seatGroups.length) seatGroup=-1;
			}
			curRow=-1;
		}
		return resultMaP;
	}
	private Integer[][] getGroupMatrix(HashMap<Integer, Integer[][]> resultMaP, int seatGroup, Integer[] currentGroup) {
		Integer[][] groupMatrix;
		if(resultMaP.get(seatGroup)!=null) {
			groupMatrix = resultMaP.get(seatGroup);
		}else {
			groupMatrix = new Integer[currentGroup[1]][currentGroup[0]];
			resultMaP.put(seatGroup,groupMatrix);
		}
		return groupMatrix;
	}
	private int max(Integer[][] seatGroups) {
		return Arrays.asList(seatGroups).stream().sorted((o1,o2)->o2[1]-o1[1]).collect(Collectors.toList()).get(0)[1];
	}
	
	private int getSeatTypeOfSeatNumber(int totalSeatGroups,int currentSeatGroupOrder,int seatColumnSizePerSeatGroup,int currenSeatOrderOfSeatGroup) {
		if((currentSeatGroupOrder==0 && currenSeatOrderOfSeatGroup==0) || (currentSeatGroupOrder==totalSeatGroups-1 && currenSeatOrderOfSeatGroup==seatColumnSizePerSeatGroup-1)) return WINDOW;
		else if(currenSeatOrderOfSeatGroup==0 || currenSeatOrderOfSeatGroup==seatColumnSizePerSeatGroup-1) return AISLE;
		else return MIDDLE;
	}
	
	public static void main(String[] args) {
		Integer[][] seatArrangements = new Integer[][] {{3,2},{4,3},{2,3},{3,4}};
		HashMap<Integer, Integer[][]> result = new AirplaneSeatAllocator().getArrangments(seatArrangements, 30);
		Set<Entry<Integer, Integer[][]>> entries = result.entrySet();
		for(Entry<Integer, Integer[][]> entry : entries) {
			System.out.println(""
					);
			for(Integer[] item: entry.getValue()) {
				System.out.println(Arrays.toString(item));
			}
		}
	}
}

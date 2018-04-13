/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clfishing.commons;

import java.util.List;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

/**
 *
 * @author jonas
 */
public class DistanceMeasure{
    
    /**
     * Gets the closest area from a list of areas
     * @param list of areas
     * @param position your current position
     * @return 
     */
    public static Area getClosestArea(List<Area> list, Position position){
        Area closestArea = null;
        int closestAreaDistance = Integer.MAX_VALUE;
        if(list.size() > 0){
            for(Area area: list){
                for(Position p: area.getPositions()){
                    int distance = position.distance(p);
                    if(distance < closestAreaDistance){
                        closestArea = area;
                        closestAreaDistance = distance;
                    }
                }
            }
        }
        return closestArea;
    }
    
    /**
     * Gets the closest position from a list of areas
     * @param list of areas
     * @param position your current position
     * @return 
     */
    public static Position getClosestPosition(List<Area> list, Position position){
        Position closestPosition = null;
        int closestAreaDistance = Integer.MAX_VALUE;
        if(list.size() > 0){
            for(Area area: list){
                for(Position p: area.getPositions()){
                    int distance = position.distance(p);
                    if(distance < closestAreaDistance){
                        closestPosition = p;
                        closestAreaDistance = distance;
                    }
                }
            }
        }
        return closestPosition;
    }
    
    /**
     * Find the closest area from a list of areas and a area
     * @param list
     * @param area
     * @return 
     */
    public static Area getClosestArea(List<Area> list, Area area){
        Area closestArea = null;
        int closestAreaDistance = Integer.MAX_VALUE;
        if(list.size() > 0){
            for(Area areaCheck: list){
                for(Position p: areaCheck.getPositions()){
                    for(Position p2: area.getPositions()){
                        int distance = p2.distance(p);
                        if(distance < closestAreaDistance){
                            closestArea = areaCheck;
                            closestAreaDistance = distance;
                        }
                    }
                }
            }
        }
        return closestArea;
    }
}

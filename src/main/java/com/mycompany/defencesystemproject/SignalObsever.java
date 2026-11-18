/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.defencesystemproject;

/**
 *
 * @author KCC
 */
public interface SignalObsever{
    
    public void isAreaClear(boolean clear);
    
    public int getAmmoCount();
    
    public int getEnergyLevel();
    
    public int getSoilderAmount();
    
    public void setMassageBoard(String sender, String massage);
    
    public void assignOperations(int strength);
    
    public boolean isPositoned();
}

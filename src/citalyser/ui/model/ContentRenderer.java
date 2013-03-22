/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.model;

import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.CollapsibleListDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.GridDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.ListDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.ProfileDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.TableDisplayPanel;

/**
 *
 * @author Tanmay Patil
 */
public interface ContentRenderer {
    
    public GridDisplayPanel getGridDisplayPanel();
    
    public ListDisplayPanel getListDisplayPanel();
    
    public ProfileDisplayPanel getProfileDisplayPanel();
    
    public TableDisplayPanel getTableDisplayPanel();
    
    public CollapsibleListDisplayPanel getCollapsibleListDisplayPanel();
    
    public void showLoading();
    
    public void clearAll();
    
    public void flipToCollapsibleListDisplayPanel();
    
    public void flipToGridDisplayPanel();
    
    public void flipToListDisplayPanel();
    
    public void flipToProfileDisplayPanel();
    
    public void flipToTableDisplayPanel();
    
}

package com.github.tavi.rep_portal.util;

/**
 * Create a new widget (Passing rate summary) on
 * the demo.reportportal.io dash-board,
 * applying random filter and setting a custom name
 */
public interface IDashboardPage {

	public IDashboardPage startAddingNewWidget();
	public IDashboardPage chooseWidgetType();
	public IDashboardPage clickNextStepButton();
	public IDashboardPage chooseFilter();
	public IDashboardPage enterWidgetName(String widgetName);
	public void finishAddingNewWidget();
	
}

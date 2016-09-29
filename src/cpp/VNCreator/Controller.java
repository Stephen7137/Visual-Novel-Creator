package cpp.VNCreator;

import cpp.VNCreator.View.Editor;
import cpp.VNCreator.View.SceneEditor;
import cpp.VNCreator.View.SimConsole;

public class Controller {
	
	ChapterEditor chEditor;
	SimConsole console;
	SceneEditor sEditor;
	Editor editor;
	
	public Controller(ChapterEditor chEditor){
		this.chEditor = chEditor;
	}
	
	public void next(int n){
		if(chEditor.next(n)){
			update();
		}		
	}
	
	public void updateSel(){
		if(chEditor.getSelected() != null){
			editor.update(chEditor.getSelected());
		}else{
			editor.clear();
		}
	}
	
	public void update(){
		if(chEditor.getCurrentNode() != null){
			console.update(chEditor.getCurrentNode());
			sEditor.update(chEditor.getCurrentNode());
		}else{
			console.clear();
			sEditor.clear();
		}
	}

	public void back() {
		chEditor.back();
		update();		
	}
}

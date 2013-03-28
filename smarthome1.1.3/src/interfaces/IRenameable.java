package interfaces;

/**
 * 可以重命名的通用接口
 * 
 * @author yinger
 * 
 */
public interface IRenameable {

	public void rename(String name) throws Exception;

	public String getOldName();

}

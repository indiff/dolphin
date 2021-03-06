#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>${module}Service</code>.
 */
public interface ${module}ServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback);
}

package com.example.bulletinboardapp.service;

import com.example.bulletinboardapp.model.Content;
import com.example.bulletinboardapp.repository.ContentRepository;
import com.example.bulletinboardapp.util.ContentUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/*
@Serviceの意味
@Serviceを付与することでこのクラスがビジネスロジックを保持していることを示したり、
アプリ起動時にSpringBootが管理してくれる。
管理されたオブジェクトは、他のオブジェクトに設定（DI）できたりする。
サービス層で使用される以外に、この注釈には特別な用途はない。
*/
@Service
public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    /*
    @ModelAttribute
    指定したモデル属性をバインドする。
    モデルにバインドさせるフォームを表示するHTMLをレンダリングする時などに変数に使用する。
    また、そのフォームのPOSTを受け取る時の変数にも@Validatedと一緒に使用する。

    Model
    htmlへモデルを渡す際に使用するオブジェクト。
    addAttributeメソッドを使用して、文字列やオブジェクトをhtmlへ渡す。
     */

    /**
     * 書き込み掲示板のホームページを表示する
     * DBに登録してあるコンテンツをテーブルで表示するのと、新たな追加フォームがある。
     * @param model htmlへモデルを渡すために使用する
     * @param content contentオブジェクトモデルにフォームの値をバインドさせるために使用する。
     * @return home.htmlを返す
     */
    public String home(Model model, @ModelAttribute Content content) {
        System.out.println("homeメソッドが呼ばれました");
        model.addAttribute("greeingMessage", ContentUtil.getMessage());
        // repositoryはJPAクラスを継承しているため、findAllメソッドが使用できる。
        // JPAクラスはデータベースと情報をやり取りするメソッドをあらかじめ一式定義している。
        model.addAttribute("contents", repository.findAll());
        return "home"; // home.htmlをレンダリングする
    }

    /*
    @Validated
    フォームなどで入力され、バインドされたモデルを受け取る時に指定する。
    @ModelAttributeと一緒に使用することが多い。

    BindingResult
    フォームなどで入力され、バインドされたモデルを受け取る時に指定する。
    @Validatedと同じ引数で使用される。
    フォームで入力された値がモデルにバインドされたかの結果オブジェクトになる。
    hasErrorsメソッドでバインドが成功したか確認できる。
     */

    /**
     * ホームページで新たに書き込みフォームがsubmitされたら、この関数で受け取り、
     * バインドの確認をした後、DBへ保存する。
     * @param content フォームで入力され、バリデイト,バインドされたContentモデル
     * @param result BindingResultオブジェクトで@Validatedの値が正常にバインドできたかを格納している
     * @param model htmlへ値を渡すためのオブジェクト
     * @return 登録が成功しても、失敗してもhome.html(ホームページ)を返す。
     */
    public String add(@Validated @ModelAttribute Content content,
                      BindingResult result,
                      Model model) {
        System.out.println("addメソッドが呼ばれました");
        model.addAttribute("contents", repository.findAll());
        if (result.hasErrors()) {
            System.out.println("バインドエラーが起きました");
            System.out.println(result.getFieldErrors()); // エラー内容を見る
            model.addAttribute("greeingMessage", "Welcom to Bulletin board app.");
            return "home";
        }
        Date nowTime = new Date();
        content.setCreatedDate(nowTime);
        repository.save(content); // オブジェクトをDBに保存するJPAにあるメソッド
        /**
        redirectした場合は、下でreturnした後はControllerのGetMapping("/")を担当している
        home関数が呼び出される。
         */
        return "redirect:/";
    }

    /**
     * IDを受け取り、DBから削除するメソッド
     * @param id 削除したいレコードのID
     * @return home.html(ホームページ)を返す
     */
    public String delete(@PathVariable Long id) {
        System.out.println("deleteメソッドが呼ばれました");
        repository.deleteById(id); // IDからレコードを削除するJpaクラスのメソッド
        return "redirect:/";
    }

    /**
     * 変更したいレコードのIDを受け取り、編集画面を返す。
     * @param id 編集したいレコードのID
     * @param model htmlへ編集したいレコードのモデルを渡すためのもの
     * @return contentEdit.html(編集画面)を返す
     */
    public String edit(@PathVariable Long id, Model model) {
        System.out.println("editメソッドが呼ばれました");
        model.addAttribute("content", repository.findById(id)); // IDからレコードを検索するJpaクラスのメソッド
        return "contentEdit";
    }

    /**
     * updateメソッド
     * @param content バインドされたContentオブジェクト
     * @param result バインドの結果
     * @param model htmlへモデルを渡すために使用する
     * @return バインドが失敗した場合は、contentEdit.html(編集画面)を返す。
     * 　　　　 バインドが成功した場合は、home.html(ホームページ)を返す。
     */
    public String update(@Validated @ModelAttribute Content content,
                      BindingResult result,
                      Model model) {
        System.out.println("updateメソッドが呼ばれました");
        model.addAttribute("contents", repository.findAll()); // DBの全てのレコードを取得するメソッド。
        if (result.hasErrors()) {
            return "contentEdit";
        }
        System.out.println(content.getCreatedDate());
        Date nowTime = new Date();
        content.setCreatedDate(nowTime);
        repository.save(content);
        return "redirect:/";
    }

}

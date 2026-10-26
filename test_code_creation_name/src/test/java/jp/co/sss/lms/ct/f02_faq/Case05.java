package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		webDriver.manage().window().setSize(new Dimension(1920, 1080));
		goTo("http://localhost:8080/lms/");

		assertEquals("ログイン | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDを入力
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		// パスワードを入力
		WebElement password = webDriver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("StudentAA012");

		// ログインボタンをクリック
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		// 画面表示を待ち、ブラウザのタブ表示が仕様通りであるかを検証
		visibilityTimeout(By.className("active"), 5);
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// ヘッダーの「機能」をクリック
		WebElement headerDropDown = webDriver.findElement(By.className("dropdown-toggle"));
		headerDropDown.click();

		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		help.click();

		// 画面表示を待ち、ブラウザのタブ表示が仕様通りであるかを検証
		visibilityTimeout(By.className("panel-title"), 5);
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」をクリック
		WebElement faq_link = webDriver.findElement(By.linkText("よくある質問"));
		faq_link.click();

		// フォーカスを「よくある質問」タブに変更
		for (String windowHandle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(windowHandle);

			if (webDriver.getTitle().contains("よくある質問")) {
				break;
			}
		}

		// 画面表示を待ち、ブラウザのタブ表示が仕様通りであるかを検証
		visibilityTimeout(By.className("form-horizontal"), 5);
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 検索キーワード入力欄に「キャンセル」と入力
		WebElement faqInputSearch = webDriver.findElement(By.className("form-control"));
		faqInputSearch.clear();
		faqInputSearch.sendKeys("キャンセル");

		// キーワード検索を実行
		WebElement searchButton = webDriver.findElement(By.cssSelector("[type = 'submit']"));
		searchButton.click();

		// 検索結果に表示された要素のテキストを検証する
		WebElement searchResult = webDriver.findElement(By.cssSelector("dt.mb10 span:nth-child(2)"));
		assertEquals("キャンセル料・途中退校について", searchResult.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 「クリア」を使用し、検索キーワード入力欄の値を消去
		WebElement clearInput = webDriver.findElement(By.cssSelector("input[value='クリア']"));
		clearInput.click();

		// 検索欄の値が空になっているかを検証
		WebElement faqInputSearch = webDriver.findElement(By.className("form-control"));
		assertEquals("", faqInputSearch.getText());

		getEvidence(new Object() {
		});
	}

}

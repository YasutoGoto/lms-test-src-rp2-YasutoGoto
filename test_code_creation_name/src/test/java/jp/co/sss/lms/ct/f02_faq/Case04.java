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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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

}

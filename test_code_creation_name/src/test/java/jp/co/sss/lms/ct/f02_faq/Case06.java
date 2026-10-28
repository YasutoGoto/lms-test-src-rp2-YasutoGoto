package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// 「研修関係」を押下
		WebElement faq_link = webDriver.findElement(By.linkText("【研修関係】"));
		faq_link.click();

		// 検索結果に表示される全ての質問
		List<WebElement> searchRusultList = webDriver.findElements(By.cssSelector("dt.mb10 span:nth-child(2)"));

		// 期待値
		List<String> expectedQuestionList = Arrays.asList(
				"キャンセル料・途中退校について",
				"研修の申し込みはどのようにすれば良いですか？");

		// 検索結果に表示された質問内容と期待値を検証
		for (int i = 0; i < searchRusultList.size(); i++) {
			String targetText = searchRusultList.get(i).getText();
			assertThat(expectedQuestionList).contains(targetText);
		}

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		List<WebElement> allQuestions = webDriver.findElements(By.className("mb10"));
		List<WebElement> allAnswers = webDriver.findElements(By.cssSelector("dd.fs18 span:nth-child(2)"));

		// 期待値
		List<String> expectedAnswersList = Arrays.asList(
				"受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。\r\n"
						+ "弊社営業担当までご相談下さい。",
				"営業担当がいる場合は、営業担当までご連絡ください。\r\n"
						+ "申し込み方法についてご案内させていただきます。\r\n"
						+ "なお、弊社営業営業がいない場合は、東京ITスクール運営事務局までご連絡いただけると幸いです。");

		// 質問に対する回答が仕様通りであるかを確認
		for (int i = 0; i < allAnswers.size(); i++) {
			String targetText = allAnswers.get(i).getText();
			assertThat(expectedAnswersList).contains(targetText);
		}

		// 表示された全質問の回答を表示
		for (int i = 0; i < allQuestions.size(); i++) {
			WebElement question = allQuestions.get(i);
			question.click();
		}

		getEvidence(new Object() {
		});

	}

}
